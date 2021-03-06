package com.ordjoy.web.controller;

import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.dto.UserTrackOrderDto;
import com.ordjoy.model.entity.order.OrderStatus;
import com.ordjoy.model.service.OrderService;
import com.ordjoy.model.service.UserService;
import com.ordjoy.model.util.LoggingUtils;
import com.ordjoy.web.util.AttributeUtils;
import com.ordjoy.web.util.PageUtils;
import com.ordjoy.web.util.UrlPathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@SessionAttributes(AttributeUtils.SESSION_USER)
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService,
                          OrderService orderService,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.orderService = orderService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Returns html page with all active users
     *
     * @param limit  for UI pagination
     * @param offset for UI pagination
     * @return html page with all active users
     * @see Model
     */
    @GetMapping("/admin/account/all")
    public String getAllUsers(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset, Model model) {
        List<UserDto> users = userService.list(limit, offset);
        Long pages = userService.getAllPages();
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.USERS, users);
        return PageUtils.USERS_PAGE;
    }

    /**
     * @param userId UserDto Identifier
     * @return html page that represents user with some info
     * @see Model
     */
    @GetMapping("/auth/account/{id}")
    public String getUserById(
            Model model,
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long userId) {
        Optional<UserDto> maybeUser = userService.findById(userId);
        if (maybeUser.isPresent()) {
            UserDto user = maybeUser.get();
            model.addAttribute(AttributeUtils.REQUEST_USER, user);
            return PageUtils.USER_PROFILE_PAGE;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param username User login
     * @return html page that represents user with some info
     * @see Model
     */
    @GetMapping("/auth/account")
    public String getUserByName(
            Model model,
            @RequestParam(value = UrlPathUtils.USERNAME_PARAM) String username) {
        Optional<UserDto> maybeUser = userService.findUserByLogin(username);
        if (maybeUser.isPresent()) {
            UserDto user = maybeUser.get();
            model.addAttribute(AttributeUtils.REQUEST_USER, user);
            return PageUtils.USER_PROFILE_PAGE;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @return login page
     * @see com.ordjoy.web.security.SecurityConfig
     * @see AuthenticationPrincipal
     */
    @GetMapping("/login")
    public String loginPage() {
        return PageUtils.LOGIN_PAGE;
    }

    /**
     * Success handler for User authority after login
     *
     * @param user User from AuthenticationPrincipal(login form)
     * @return welcome html page depends on user authority
     * @see Model
     * @see org.springframework.security.core.GrantedAuthority
     * @see com.ordjoy.model.entity.user.Role
     */
    @GetMapping("/success")
    public String success(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
            Model model) {
        Optional<UserDto> maybeUserToSync = userService.findUserByLogin(user.getUsername());
        if (maybeUserToSync.isPresent()) {
            UserDto userToSync = maybeUserToSync.get();
            Optional<UserDto> maybeUser = userService.getSynchronizedUser(userToSync.getId());
            if (maybeUser.isPresent()) {
                UserDto userDto = maybeUser.get();
                switch (userDto.getRole()) {
                    case USER -> {
                        model.addAttribute(AttributeUtils.SESSION_USER, userDto);
                        return UrlPathUtils.REDIRECT_USER_MAIN_PAGE;
                    }
                    case ADMIN -> {
                        model.addAttribute(AttributeUtils.SESSION_USER, userDto);
                        return UrlPathUtils.REDIRECT_ADMIN_MAIN_PAGE;
                    }
                    default -> throw new IllegalStateException
                            (AttributeUtils.ROLE_UNDEFINED_EXCEPTION_MESSAGE + userDto.getRole());
                }
            } else {
                return UrlPathUtils.REDIRECT_LOGIN;
            }
        } else {
            return UrlPathUtils.REDIRECT_LOGIN;
        }
    }

    /**
     * @return add new Admin form
     */
    @GetMapping("/admin/account/admin-main")
    public String adminMainPage() {
        return PageUtils.ADMIN_PAGE;
    }

    /**
     * @return html page that represents application metrics
     */
    @GetMapping("/admin/system/metrics")
    public String metricsPage() {
        return PageUtils.METRICS_PAGE;
    }

    /**
     * @return user welcome page
     */
    @GetMapping("/user/account/welcome")
    public String welcomePage() {
        return PageUtils.USER_WELCOME_PAGE;
    }

    /**
     * Shows user info page sync with DB to avoid dirty reads & data inconsistency
     *
     * @param userDto User from current session
     * @return user info page
     * @see Model
     */
    @GetMapping("/user/account/info")
    public String showUserInfo(
            @SessionAttribute(AttributeUtils.SESSION_USER) UserDto userDto,
            Model model) {
        Optional<UserDto> maybeUser = userService.getSynchronizedUser(userDto.getId());
        if (maybeUser.isPresent()) {
            UserDto actual = maybeUser.get();
            model.addAttribute(AttributeUtils.SESSION_USER, actual);
        }
        return PageUtils.USER_INFO_PAGE;
    }

    /**
     * logout endpoint
     *
     * @return redirect to login page
     * @see SessionStatus
     */
    @PostMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return UrlPathUtils.REDIRECT_LOGIN;
    }

    /**
     * Deletes user by its id
     *
     * @param id UserDto Identifier
     * @return redirect to all active users page
     */
    @GetMapping("/admin/account/{id}/remove")
    public String deleteUser(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long id) {
        userService.delete(id);
        log.debug(LoggingUtils.USER_WAS_DELETED_IN_CONTROLLER, id);
        return UrlPathUtils.REDIRECT_USERS_PAGE;
    }

    /**
     * @return set optional info html form
     */
    @GetMapping("/user/account/info/set-optional-info")
    public String userOptionalInfoFormPage() {
        return PageUtils.SET_OPTIONAL_INFO_PAGE;
    }

    /**
     * Sets optional user info
     *
     * @param firstName  UserDto firstName to set
     * @param lastName   UserDto lastName to set
     * @param cardNumber UserDto cardNumber to set
     * @param userDto    UserDto from current session
     * @return redirect to user account info page
     */
    @PostMapping("/user/account/info/set-optional-info")
    public String setOptionalInfo(
            String firstName,
            String lastName,
            String cardNumber,
            @SessionAttribute(AttributeUtils.SESSION_USER) UserDto userDto) {
        if (firstName != null && !"".equals(firstName) && !firstName.isBlank()) {
            userDto.getPersonalInfo().setFirstName(firstName);
        }
        if (lastName != null && !"".equals(lastName) && !lastName.isBlank()) {
            userDto.getPersonalInfo().setLastName(lastName);
        }
        if (cardNumber != null && !"".equals(cardNumber) && !cardNumber.isBlank()) {
            userDto.getPersonalInfo().setCardNumber(cardNumber);
        }
        userService.update(userDto);
        return UrlPathUtils.REDIRECT_ACCOUNT_INFO;
    }

    /**
     * @return user update account balance form page
     */
    @GetMapping("/user/account/info/deposit-form")
    public String getUserDepositFormPage() {
        return PageUtils.UPDATE_BALANCE_PAGE;
    }

    /**
     * Updates user account balance
     *
     * @param balanceToAdd balance amount that be added to current account balance
     * @param userId       UserDto Identifier
     * @return redirect to user account info page
     */
    @PostMapping("/user/account/info/add-balance/{id}")
    public String updateUserAccountBalance(
            BigDecimal balanceToAdd,
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long userId) {
        userService.updateUserBalanceAmount(balanceToAdd, userId);
        log.debug(LoggingUtils.USER_BALANCE_WAS_UPDATED_IN_CONTROLLER, balanceToAdd, userId);
        return UrlPathUtils.REDIRECT_ACCOUNT_INFO;
    }

    /**
     * User pays order by its id
     *
     * @param userDto UserDto from current session
     * @param orderId UserTrackOrderDto Identifier
     * @return all active user orders
     */
    @GetMapping("/user/pay-order/{id}")
    public String payOrder(
            @SessionAttribute(AttributeUtils.SESSION_USER) UserDto userDto,
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long orderId) {
        Optional<UserTrackOrderDto> maybeOrder = orderService.findById(orderId);
        if (maybeOrder.isPresent()) {
            UserTrackOrderDto userTrackOrderDto = maybeOrder.get();
            userService.subtractBalanceFromUser(userTrackOrderDto.getPrice(), userDto.getId());
            log.debug(LoggingUtils.USER_BALANCE_WAS_SUBTRACTED_IN_CONTROLLER, userDto, userDto);
            orderService.updateOrderStatus(OrderStatus.COMPLETED, orderId);
            log.debug(LoggingUtils.ORDER_STATUS_WAS_UPDATED_IN_CONTROLLER,
                    userTrackOrderDto.getStatus(), orderId);
        }
        return UrlPathUtils.REDIRECT_USER_ORDERS_PAGE;
    }

    /**
     * @return user update discount percentage level form page
     */
    @GetMapping("/admin/info/update-discount-level/{id}")
    public String updateUserDiscountPercentageLevelPage(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long id,
            Model model) {
        Optional<UserDto> maybeUser = userService.findById(id);
        maybeUser.ifPresent(userDto -> model.addAttribute(AttributeUtils.REQUEST_USER, userDto));
        return PageUtils.UPDATE_DPL_PAGE;
    }

    /**
     * Update users discount percentage level
     *
     * @param userDto UserDto whom discount will be updated
     * @return redirect to all active users page
     */
    @PostMapping("/admin/info/update-discount-level")
    public String updateUserDiscountPercentageLevel(UserDto userDto) {
        Integer discountPercentageLevel = userDto.getPersonalInfo().getDiscountPercentageLevel();
        Long id = userDto.getId();
        userService.updateDiscountPercentageLevel(discountPercentageLevel, id);
        log.debug(LoggingUtils.USER_DISCOUNT_PERCENTAGE_LEVEL_WAS_UPDATED_IN_CONTROLLER,
                discountPercentageLevel, id);
        return UrlPathUtils.REDIRECT_USERS_PAGE;
    }

    /**
     * @return register form(page)
     * @see Model
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute(AttributeUtils.SESSION_USER, UserDto.builder().build());
        return PageUtils.REGISTRATION_PAGE;
    }

    /**
     * User Registration using UsernamePasswordAuthenticationToken
     *
     * @param user UserDto from the UI form
     * @return redirect to user main page
     * @see HttpServletRequest
     * @see org.springframework.security.core.userdetails.UserDetailsService
     * @see com.ordjoy.web.security.SecurityConfig
     */
    @PostMapping("/register")
    public String register(UserDto user, Model model, HttpServletRequest request) {
        if (userService.isUserHasRightsToRegister(user) &&
            !userService.isEmailExists(user.getEmail()) &&
            !userService.isLoginExists(user.getLogin())) {
            String rawPassword = user.getPassword();
            user.setPassword(passwordEncoder.encode(rawPassword));
            UserDto savedUser = userService.save(user);
            model.addAttribute(AttributeUtils.SESSION_USER, savedUser);
            log.debug(LoggingUtils.USER_WAS_REGISTERED_CONTROLLER, savedUser);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(savedUser.getLogin(), rawPassword);
            authenticationToken.setDetails(new WebAuthenticationDetails(request));
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return UrlPathUtils.REDIRECT_USER_MAIN_PAGE;
        } else {
            return UrlPathUtils.REDIRECT_REGISTRATION_PAGE;
        }
    }

    /**
     * @return add new Admin form(page)
     * @see Model
     */
    @GetMapping("/admin/account/add-admin")
    public String addNewAdmin(Model model) {
        model.addAttribute(AttributeUtils.ADMIN, UserDto.builder().build());
        return PageUtils.ADD_ADMIN_FORM;
    }

    /**
     * Adds new Admin in the system
     *
     * @param adminDto AdminDto from UI form
     * @return default admin page
     */
    @PostMapping("/admin/account/add-admin")
    public String addNewAdmin(UserDto adminDto) {
        if (userService.isUserHasRightsToRegister(adminDto) &&
            !userService.isEmailExists(adminDto.getEmail()) &&
            !userService.isLoginExists(adminDto.getLogin())) {
            String rawPassword = adminDto.getPassword();
            adminDto.setPassword(passwordEncoder.encode(rawPassword));
            UserDto savedAdmin = userService.addNewAdmin(adminDto);
            log.debug(LoggingUtils.NEW_ADMIN_WAS_ADDED_CONTROLLER, savedAdmin);
            return UrlPathUtils.REDIRECT_ADMIN_MAIN_PAGE;
        } else {
            return UrlPathUtils.REDIRECT_ADD_ADMIN_FORM;
        }
    }
}