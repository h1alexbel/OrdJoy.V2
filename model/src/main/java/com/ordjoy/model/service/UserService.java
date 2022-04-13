package com.ordjoy.model.service;

import com.ordjoy.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserService extends UserDetailsService, GenericCRUDService<UserDto, Long> {

    boolean isUserHasRightsToRegister(UserDto userDto);

    UserDto addNewAdmin(UserDto userDto);

    void subtractBalanceFromUser(BigDecimal orderCost, Long userId);

    void updateDiscountPercentageLevel(Integer valueToSet, Long userId);

    void updateUserBalanceAmount(BigDecimal amountToAdd, Long userId);

    Optional<UserDto> findUserByEmail(String email);

    Optional<UserDto> getSynchronizedUser(Long id);

    Optional<UserDto> findUserByLogin(String login);

    boolean isEmailExists(String email);

    boolean isLoginExists(String login);

    void deleteUser(Long userId);
}