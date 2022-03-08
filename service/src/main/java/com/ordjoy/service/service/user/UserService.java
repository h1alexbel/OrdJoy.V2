package com.ordjoy.service.service.user;

import com.ordjoy.database.model.user.User;
import com.ordjoy.service.dto.ReviewDto;
import com.ordjoy.service.dto.UserDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> listUsers();

    UserDto saveUser(User user);

    void updateDiscountPercentageLevel(Integer valueToSet, Long userId);

    void updateUserBalanceAmount(BigDecimal amountToAdd, Long userId);

    Optional<UserDto> findUserByLoginAndPassword(String login, String password);

    Optional<UserDto> findUserById(Long id);

    List<ReviewDto> findReviewsByUserLogin(String login);

    boolean isEmailExists(String email);

    boolean isLoginExists(String login);

    void updateUser(User user);

    void deleteUserById(Long id);
}