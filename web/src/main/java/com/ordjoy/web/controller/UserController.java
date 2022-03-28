package com.ordjoy.web.controller;

import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.service.user.UserService;
import com.ordjoy.model.util.LoggingUtils;
import com.ordjoy.web.util.AttributeUtils;
import com.ordjoy.web.util.PageUtils;
import com.ordjoy.web.util.UrlPathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/account")
@SessionAttributes(AttributeUtils.SESSION_USER)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/all")
    public String getAllUsers(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset, Model model) {
        List<UserDto> users = userService.listUsers(limit, offset);
        model.addAttribute(AttributeUtils.USERS, users);
        return PageUtils.USERS_PAGE;
    }

    @GetMapping("/{id}")
    public String getUserById(
            Model model,
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long userId) {
        Optional<UserDto> maybeUser = userService.findUserById(userId);
        if (maybeUser.isPresent()) {
            UserDto user = maybeUser.get();
            model.addAttribute(AttributeUtils.REQUEST_USER, user);
            return PageUtils.USER_PROFILE_PAGE;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
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

    @GetMapping("/login")
    public String loginPage() {
        return PageUtils.LOGIN_PAGE;
    }

    @PostMapping("/login")
    public String login(UserDto userDto, Model model) {
        Optional<UserDto> maybeUser = userService
                .findUserByLoginAndPassword(userDto.getLogin(), userDto.getPassword());
        if (maybeUser.isPresent()) {
            UserDto user = maybeUser.get();
            model.addAttribute(AttributeUtils.SESSION_USER, user);
            log.debug(LoggingUtils.USER_WAS_LOGIN_IN_CONTROLLER, user);
            return UrlPathUtils.REDIRECT_WELCOME_PAGE;
        } else {
            return PageUtils.LOGIN_PAGE;
        }
    }

    @GetMapping("/welcome")
    public String welcomePage() {
        return PageUtils.USER_WELCOME_PAGE;
    }

    @GetMapping("/info")
    public String showUserInfo() {
        return PageUtils.USER_INFO_PAGE;
    }

    @PostMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return UrlPathUtils.REDIRECT_LOGIN_PAGE;
    }

    @PostMapping("/{id}/remove")
    public String deleteUser(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long id) {
        userService.deleteUser(id);
        log.debug(LoggingUtils.USER_WAS_DELETED_IN_CONTROLLER, id);
        return UrlPathUtils.REDIRECT_USERS_PAGE;
    }

    @PostMapping("admin/update")
    public String updateUser(UserDto userDto) {
        if (!userService.isLoginExists(userDto.getLogin()) &&
            !userService.isEmailExists(userDto.getEmail())) {
            userService.updateUser(userDto);
            log.debug(LoggingUtils.USER_WAS_UPDATED_IN_CONTROLLER, userDto);
            return UrlPathUtils.REDIRECT_USERS_PAGE;
        } else {
            return PageUtils.USER_UPDATE_FORM;
        }
    }

    @PostMapping("admin/info/updateDiscountLevel")
    public String updateUserDiscountPercentageLevel(Integer discountPercentageLevelToSet, Long userId) {
        userService.updateDiscountPercentageLevel(discountPercentageLevelToSet, userId);
        log.debug(LoggingUtils.USER_DISCOUNT_PERCENTAGE_LEVEL_WAS_UPDATED_IN_CONTROLLER,
                discountPercentageLevelToSet, userId);
        return UrlPathUtils.REDIRECT_USERS_PAGE;
    }

    @PostMapping("/info/addBalance/{id}")
    public String updateUserAccountBalance(
            BigDecimal balanceToAdd,
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long userId) {
        userService.updateUserBalanceAmount(balanceToAdd, userId);
        log.debug(LoggingUtils.USER_BALANCE_WAS_UPDATED_IN_CONTROLLER, balanceToAdd, userId);
        return UrlPathUtils.REDIRECT_ACCOUNT_INFO;
    }

    @GetMapping("/register")
    public String register() {
        return PageUtils.REGISTRATION_PAGE;
    }

    @PostMapping("/register")
    public String register(UserDto userDto, Model model) {
        if (userService.isUserHasRightsToRegister(userDto) &&
            !userService.isEmailExists(userDto.getEmail()) &&
            !userService.isLoginExists(userDto.getLogin())) {
            UserDto savedUser = userService.saveUser(userDto);
            model.addAttribute(AttributeUtils.SESSION_ORDER, savedUser);
            log.debug(LoggingUtils.USER_WAS_REGISTERED_CONTROLLER, savedUser);
            return UrlPathUtils.REDIRECT_WELCOME_PAGE;
        } else {
            return PageUtils.REGISTRATION_PAGE;
        }
    }

    @GetMapping("/admin/addAdmin")
    public String addNewAdmin() {
        return PageUtils.ADD_ADMIN_FORM;
    }

    @PostMapping("/admin/addAdmin")
    public String addNewAdmin(UserDto adminDto, Model model) {
        if (userService.isUserHasRightsToRegister(adminDto) &&
            !userService.isEmailExists(adminDto.getEmail()) &&
            !userService.isLoginExists(adminDto.getLogin())) {
            UserDto savedAdmin = userService.addNewAdmin(adminDto);
            model.addAttribute(AttributeUtils.SESSION_ORDER, savedAdmin);
            log.debug(LoggingUtils.NEW_ADMIN_WAS_ADDED_CONTROLLER, savedAdmin);
            return UrlPathUtils.REDIRECT_ADMIN_MAIN_PAGE;
        } else {
            return PageUtils.ADD_ADMIN_FORM;
        }
    }
}