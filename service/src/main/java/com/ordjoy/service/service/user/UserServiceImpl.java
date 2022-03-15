package com.ordjoy.service.service.user;

import com.ordjoy.database.model.user.User;
import com.ordjoy.database.repository.user.UserRepository;
import com.ordjoy.service.dto.UserDto;
import com.ordjoy.service.dto.UserPersonalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Integer MIN_AGE_TO_REGISTER = 13;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> listUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .login(user.getLogin())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .personalInfo(UserPersonalInfo.builder()
                                .discountPercentageLevel(user.getUserData()
                                        .getDiscountPercentageLevel())
                                .build())
                        .build())
                .toList();
    }

    @Transactional
    @Override
    public UserDto saveUser(User user) {
        User savedUser = userRepository.add(user);
        UserDto userDto = UserDto.builder()
                .id(savedUser.getId())
                .login(savedUser.getLogin())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .personalInfo(UserPersonalInfo.builder()
                        .birthDate(savedUser.getUserData().getBirthDate())
                        .discountPercentageLevel(savedUser.getUserData()
                                .getDiscountPercentageLevel())
                        .accountBalance(savedUser.getUserData().getAccountBalance())
                        .build())
                .build();
        if (user.getUserData().getFirstName() != null) {
            userDto.getPersonalInfo().setFirstName(user.getUserData().getFirstName());
        }
        if (user.getUserData().getLastName() != null) {
            userDto.getPersonalInfo().setLastName(user.getUserData().getLastName());
        }
        return userDto;
    }

    @Override
    public boolean isUserHasRightsToRegister(User user) {
        boolean result = false;
        if (user != null) {
            long userAge = getUserAge(user.getUserData().getBirthDate());
            if (userAge >= MIN_AGE_TO_REGISTER) {
                result = true;
            }
        }
        return result;
    }

    private long getUserAge(LocalDate userBirthDate) {
        return ChronoUnit.YEARS.between(userBirthDate, LocalDate.now());
    }

    @Transactional
    @Override
    public void updateDiscountPercentageLevel(Integer valueToSet, Long userId) {
        if (valueToSet != null && userId != null) {
            userRepository.updateDiscountLevel(valueToSet, userId);
        }
    }

    @Transactional
    @Override
    public void updateUserBalanceAmount(BigDecimal amountToAdd, Long userId) {
        if (amountToAdd != null && userId != null) {
            userRepository.updateBalanceAmount(amountToAdd, userId);
        }
    }

    @Override
    public Optional<UserDto> findUserByLoginAndPassword(String login, String password) {
        if (login != null && password != null) {
            return userRepository.findByLoginAndPassword(login, password).stream()
                    .map(user -> UserDto.builder()
                            .id(user.getId())
                            .login(user.getLogin())
                            .email(user.getEmail())
                            .personalInfo(UserPersonalInfo.builder()
                                    .birthDate(user.getUserData().getBirthDate())
                                    .discountPercentageLevel(user.getUserData()
                                            .getDiscountPercentageLevel())
                                    .build())
                            .role(user.getRole())
                            .build())
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> findUserById(Long id) {
        if (id != null) {
            return userRepository.findById(id).stream()
                    .map(user -> UserDto.builder()
                            .id(user.getId())
                            .login(user.getLogin())
                            .email(user.getEmail())
                            .personalInfo(UserPersonalInfo.builder()
                                    .birthDate(user.getUserData().getBirthDate())
                                    .discountPercentageLevel(user.getUserData()
                                            .getDiscountPercentageLevel())
                                    .build())
                            .role(user.getRole())
                            .build())
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
                            .personalInfo(UserPersonalInfo.builder()
                                    .birthDate(user.getUserData().getBirthDate())
                                    .discountPercentageLevel(user.getUserData()
                                            .getDiscountPercentageLevel())
                                    .build())
                            .role(user.getRole())
                            .build())
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
                            .personalInfo(UserPersonalInfo.builder()
                                    .birthDate(user.getUserData().getBirthDate())
                                    .discountPercentageLevel(user.getUserData()
                                            .getDiscountPercentageLevel())
                                    .build())
                            .role(user.getRole())
                            .build())
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public boolean isEmailExists(String email) {
        AtomicBoolean result = new AtomicBoolean(false);
        if (email != null) {
            userRepository.findByEmail(email)
                    .ifPresent(user -> result.set(true));
        }
        return result.get();
    }

    @Override
    public boolean isLoginExists(String login) {
        AtomicBoolean result = new AtomicBoolean(false);
        if (login != null) {
            userRepository.findByLogin(login)
                    .ifPresent(user -> result.set(true));
        }
        return result.get();
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        if (user != null) {
            userRepository.update(user);
        }
    }

    @Transactional
    @Override
    public void deleteUser(User user) {
        if (user != null) {
            userRepository.delete(user);
        }
    }
}