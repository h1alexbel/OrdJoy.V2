package com.ordjoy.model.service.impl;

import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.dto.UserPersonalInfo;
import com.ordjoy.model.entity.order.UserTrackOrder;
import com.ordjoy.model.entity.review.AlbumReview;
import com.ordjoy.model.entity.review.MixReview;
import com.ordjoy.model.entity.review.TrackReview;
import com.ordjoy.model.entity.user.Role;
import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.entity.user.UserData;
import com.ordjoy.model.repository.AlbumReviewRepository;
import com.ordjoy.model.repository.MixReviewRepository;
import com.ordjoy.model.repository.OrderRepository;
import com.ordjoy.model.repository.TrackReviewRepository;
import com.ordjoy.model.repository.UserRepository;
import com.ordjoy.model.service.UserService;
import com.ordjoy.model.util.LoggingUtils;
import com.ordjoy.model.util.PaginationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final AlbumReviewRepository albumReviewRepository;
    private final MixReviewRepository mixReviewRepository;
    private final TrackReviewRepository trackReviewRepository;
    private static final int STARTER_DISCOUNT_PERCENTAGE_LEVEL = 0;
    private static final int ADMIN_DISCOUNT_PERCENTAGE_LEVEL = 0;
    private static final BigDecimal ADMIN_BALANCE = new BigDecimal(0);
    private static final BigDecimal STARTER_ACCOUNT_BALANCE = new BigDecimal(0);
    private static final String USER_NOT_FOUND_MESSAGE = "Can't find username with login:";
    private static final Integer MIN_AGE_TO_REGISTER = 13;
    private final Function<User, UserDetails> userToUserDetails = user ->
            org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getLogin())
                    .password(user.getPassword())
                    .authorities(user.getRole().name())
                    .build();

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           OrderRepository orderRepository,
                           TrackReviewRepository trackReviewRepository,
                           AlbumReviewRepository albumReviewRepository,
                           MixReviewRepository mixReviewRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.albumReviewRepository = albumReviewRepository;
        this.mixReviewRepository = mixReviewRepository;
        this.trackReviewRepository = trackReviewRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> maybeUser = userRepository.findByLogin(username);
        UserDetails userDetails = maybeUser.map(userToUserDetails)
                .orElseThrow(() ->
                        new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE + username));
        log.debug(LoggingUtils.USER_DETAILS_WAS_TRIGGERED, userDetails);
        return userDetails;
    }

    @Override
    public List<UserDto> list(int limit, int offset) {
        return userRepository.findAll(limit, offset).stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .login(user.getLogin())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .personalInfo(UserPersonalInfo.builder()
                                .discountPercentageLevel(user.getUserData()
                                        .getDiscountPercentageLevel())
                                .birthDate(user.getUserData().getBirthDate())
                                .build())
                        .build())
                .toList();
    }

    @Transactional
    @Override
    public UserDto save(UserDto userDto) {
        User user = User.builder()
                .login(userDto.getLogin())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(Role.USER)
                .userData(UserData.builder()
                        .birthDate(userDto.getPersonalInfo().getBirthDate())
                        .discountPercentageLevel(STARTER_DISCOUNT_PERCENTAGE_LEVEL)
                        .accountBalance(STARTER_ACCOUNT_BALANCE)
                        .build())
                .build();
        setOptionalInfoToUserEntity(userDto, user);
        User saved = userRepository.add(user);
        log.debug(LoggingUtils.USER_WAS_SAVED_IN_SERVICE, user);
        UserDto userDtoWithId = UserDto.builder()
                .id(saved.getId())
                .login(saved.getLogin())
                .email(saved.getEmail())
                .password(saved.getPassword())
                .role(saved.getRole())
                .personalInfo(UserPersonalInfo.builder()
                        .birthDate(saved.getUserData().getBirthDate())
                        .discountPercentageLevel(saved.getUserData().getDiscountPercentageLevel())
                        .accountBalance(saved.getUserData().getAccountBalance())
                        .build())
                .build();
        setOptionalInfoToUserDto(saved, userDtoWithId);
        return userDtoWithId;
    }

    @Transactional
    @Override
    public UserDto addNewAdmin(UserDto adminDto) {
        User admin = User.builder()
                .login(adminDto.getLogin())
                .email(adminDto.getEmail())
                .password(adminDto.getPassword())
                .role(Role.ADMIN)
                .userData(UserData.builder()
                        .birthDate(adminDto.getPersonalInfo().getBirthDate())
                        .discountPercentageLevel(ADMIN_DISCOUNT_PERCENTAGE_LEVEL)
                        .accountBalance(ADMIN_BALANCE)
                        .build())
                .build();
        setOptionalInfoToUserEntity(adminDto, admin);
        User saved = userRepository.add(admin);
        log.debug(LoggingUtils.ADMIN_WAS_SAVED_IN_SERVICE, admin);
        UserDto adminDtoWithId = UserDto.builder()
                .id(saved.getId())
                .login(saved.getLogin())
                .email(saved.getEmail())
                .password(saved.getPassword())
                .role(saved.getRole())
                .personalInfo(UserPersonalInfo.builder()
                        .birthDate(saved.getUserData().getBirthDate())
                        .discountPercentageLevel(saved.getUserData().getDiscountPercentageLevel())
                        .accountBalance(saved.getUserData().getAccountBalance())
                        .build())
                .build();
        setOptionalInfoToUserDto(saved, adminDtoWithId);
        return adminDtoWithId;
    }

    @Override
    public boolean isUserHasRightsToRegister(UserDto userDto) {
        boolean result = false;
        if (userDto != null) {
            long userAge = getUserAge(userDto.getPersonalInfo().getBirthDate());
            if (userAge >= MIN_AGE_TO_REGISTER) {
                result = true;
            }
        }
        log.debug(LoggingUtils.USER_RIGHTS_TO_REGISTER, result);
        return result;
    }

    @Transactional
    @Override
    public void updateDiscountPercentageLevel(Integer valueToSet, Long userId) {
        if (valueToSet != null && userId != null) {
            Optional<User> maybeUser = userRepository.findById(userId);
            maybeUser.ifPresent(user -> userRepository.updateDiscountLevel(valueToSet, user.getId()));
            log.debug(LoggingUtils.USER_DISCOUNT_PERCENTAGE_LEVEL_WAS_UPDATED_SERVICE, valueToSet, userId);
        }
    }

    @Transactional
    @Override
    public void subtractBalanceFromUser(BigDecimal orderCost, Long userId) {
        if (orderCost != null && userId != null) {
            Optional<User> maybeUser = userRepository.findById(userId);
            maybeUser.ifPresent(user -> {
                userRepository.subtractBalance(orderCost, user.getId());
                log.debug(LoggingUtils.USER_BALANCE_WAS_SUBTRACTED_SERVICE, orderCost, user);
            });
        }
    }

    @Transactional
    @Override
    public void updateUserBalanceAmount(BigDecimal amountToAdd, Long userId) {
        if (amountToAdd != null && userId != null) {
            Optional<User> maybeUser = userRepository.findById(userId);
            maybeUser.ifPresent(user -> userRepository.updateBalanceAmount(amountToAdd, userId));
            log.debug(LoggingUtils.USER_BALANCE_WAS_UPDATED_SERVICE, amountToAdd, userId);
        }
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        if (id != null) {
            return userRepository.findById(id).stream()
                    .map(user -> {
                        UserDto userDto = UserDto.builder()
                                .id(user.getId())
                                .login(user.getLogin())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .personalInfo(UserPersonalInfo.builder()
                                        .accountBalance(user.getUserData().getAccountBalance())
                                        .discountPercentageLevel(user.getUserData()
                                                .getDiscountPercentageLevel())
                                        .build())
                                .build();
                        setOptionalInfoToUserDto(user, userDto);
                        return userDto;
                    })
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> findUserByEmail(String email) {
        if (email != null) {
            return userRepository.findByEmail(email).stream()
                    .map(user -> UserDto.builder()
                            .id(user.getId())
                            .login(user.getLogin())
                            .email(user.getEmail())
                            .role(user.getRole())
                            .personalInfo(UserPersonalInfo.builder()
                                    .discountPercentageLevel(user.getUserData()
                                            .getDiscountPercentageLevel())
                                    .accountBalance(user.getUserData().getAccountBalance())
                                    .build())
                            .build())
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> getSynchronizedUser(Long id) {
        if (id != null) {
            return userRepository.findById(id).stream()
                    .map(user -> {
                        UserDto userDto = UserDto.builder()
                                .id(user.getId())
                                .login(user.getLogin())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .personalInfo(UserPersonalInfo.builder()
                                        .birthDate(user.getUserData().getBirthDate())
                                        .accountBalance(user.getUserData().getAccountBalance())
                                        .discountPercentageLevel(user.getUserData()
                                                .getDiscountPercentageLevel())
                                        .build())
                                .build();
                        setOptionalInfoToUserDto(user, userDto);
                        return userDto;
                    })
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> findUserByLogin(String login) {
        if (login != null) {
            return userRepository.findByLogin(login).stream()
                    .map(user -> UserDto.builder()
                            .id(user.getId())
                            .login(user.getLogin())
                            .email(user.getEmail())
                            .role(user.getRole())
                            .personalInfo(UserPersonalInfo.builder()
                                    .discountPercentageLevel(user.getUserData()
                                            .getDiscountPercentageLevel())
                                    .accountBalance(user.getUserData().getAccountBalance())
                                    .build())
                            .build())
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public boolean isEmailExists(String email) {
        AtomicBoolean isEmailExistsResult = new AtomicBoolean(false);
        if (email != null) {
            userRepository.findByEmail(email)
                    .ifPresent(user -> {
                        isEmailExistsResult.set(true);
                        log.debug(LoggingUtils.IS_USER_EMAIL_EXISTS, user, isEmailExistsResult);
                    });
        }
        return isEmailExistsResult.get();
    }

    @Override
    public boolean isLoginExists(String login) {
        AtomicBoolean isLoginExistsResult = new AtomicBoolean(false);
        if (login != null) {
            userRepository.findByLogin(login)
                    .ifPresent(user -> {
                        isLoginExistsResult.set(true);
                        log.debug(LoggingUtils.IS_USER_LOGIN_EXISTS, user, isLoginExistsResult);
                    });
        }
        return isLoginExistsResult.get();
    }

    @Transactional
    @Override
    public void update(UserDto userDto) {
        if (userDto != null) {
            userRepository.update(mapEntityFromDto(userDto));
            log.debug(LoggingUtils.USER_WAS_UPDATED_SERVICE, userDto);
        }
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        if (userId != null) {
            Optional<User> maybeUser = userRepository.findById(userId);
            maybeUser.ifPresent(user -> {
                List<UserTrackOrder> ordersByUserId = orderRepository.findOrdersByUserId(userId);
                for (UserTrackOrder userTrackOrder : ordersByUserId) {
                    orderRepository.delete(userTrackOrder);
                }
                List<AlbumReview> albumReviews = albumReviewRepository.findAlbumReviewsByUserId(userId);
                for (AlbumReview albumReview : albumReviews) {
                    albumReviewRepository.delete(albumReview);
                }
                List<MixReview> mixReviews = mixReviewRepository.findMixReviewsByUserId(userId);
                for (MixReview mixReview : mixReviews) {
                    mixReviewRepository.delete(mixReview);
                }
                List<TrackReview> trackReviews = trackReviewRepository.findTrackReviewsByUserId(userId);
                for (TrackReview trackReview : trackReviews) {
                    trackReviewRepository.delete(trackReview);
                }
                userRepository.delete(user);
                log.debug(LoggingUtils.USER_WAS_DELETED_SERVICE, user);
            });
        }
    }

    @Override
    public Long getAllPages() {
        return PaginationUtils.collectToPages(userRepository.getAllRecords());
    }

    private User mapEntityFromDto(UserDto userDto) {
        User user = User.builder()
                .login(userDto.getLogin())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .userData(UserData.builder()
                        .accountBalance(userDto.getPersonalInfo().getAccountBalance())
                        .birthDate(userDto.getPersonalInfo().getBirthDate())
                        .discountPercentageLevel(userDto.getPersonalInfo()
                                .getDiscountPercentageLevel())
                        .build())
                .build();
        user.setId(userDto.getId());
        setOptionalInfoToUserEntity(userDto, user);
        return user;
    }

    private long getUserAge(LocalDate userBirthDate) {
        return ChronoUnit.YEARS.between(userBirthDate, LocalDate.now());
    }

    private void setOptionalInfoToUserDto(User user, UserDto userDto) {
        if (user.getUserData().getFirstName() != null) {
            userDto.getPersonalInfo().setFirstName(user.getUserData().getFirstName());
        }
        if (user.getUserData().getLastName() != null) {
            userDto.getPersonalInfo().setLastName(user.getUserData().getLastName());
        }
        if (user.getUserData().getCardNumber() != null) {
            userDto.getPersonalInfo().setCardNumber(user.getUserData().getCardNumber());
        }
    }

    private void setOptionalInfoToUserEntity(UserDto userDto, User user) {
        if (userDto.getPersonalInfo().getFirstName() != null) {
            user.getUserData().setFirstName(userDto.getPersonalInfo().getFirstName());
        }
        if (userDto.getPersonalInfo().getLastName() != null) {
            user.getUserData().setLastName(userDto.getPersonalInfo().getLastName());
        }
        if (userDto.getPersonalInfo().getCardNumber() != null) {
            user.getUserData().setCardNumber(userDto.getPersonalInfo().getCardNumber());
        }
    }
}