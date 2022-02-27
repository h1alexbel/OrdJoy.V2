package com.ordjoy.database.repository.user;

import com.ordjoy.database.model.review.Review;
import com.ordjoy.database.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void updateDiscountLevel(Integer newDiscountLevelToSet, Long userId);

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByLoginAndPassword(String login, String password);

    List<Review> findReviewsByUserLogin(String login);
}