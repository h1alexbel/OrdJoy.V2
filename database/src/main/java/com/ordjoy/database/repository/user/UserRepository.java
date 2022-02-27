package com.ordjoy.database.repository.user;

import com.ordjoy.database.model.review.Review;
import com.ordjoy.database.model.user.User;
import com.ordjoy.database.repository.GenericCRUDRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends GenericCRUDRepository<User, Long> {

    void updateDiscountLevel(Integer newDiscountLevelToSet, Long userId);

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByLoginAndPassword(String login, String password);

    List<Review<Long>> findReviewsByUserLogin(String login);
}