package com.ordjoy.model.service.user;

import com.ordjoy.model.dto.UserDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> listUsers();

    boolean isUserHasRightsToRegister(UserDto userDto);

    UserDto saveUser(UserDto user);

    UserDto addNewAdmin(UserDto userDto);

    void updateDiscountPercentageLevel(Integer valueToSet, Long userId);

    void updateUserBalanceAmount(BigDecimal amountToAdd, Long userId);

    Optional<UserDto> findUserByLoginAndPassword(String login, String password);

    Optional<UserDto> findUserById(Long id);

    Optional<UserDto> findUserByEmail(String email);

    Optional<UserDto> findUserByLogin(String login);

    boolean isEmailExists(String email);

    boolean isLoginExists(String login);

    void updateUser(UserDto userDto);

    void deleteUser(UserDto userDto);
}