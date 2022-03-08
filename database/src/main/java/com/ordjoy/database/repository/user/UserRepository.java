package com.ordjoy.database.repository.user;

import com.ordjoy.database.model.review.Review;
import com.ordjoy.database.model.user.User;
import com.ordjoy.database.repository.GenericCRUDRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends GenericCRUDRepository<User, Long> {

    void updateDiscountLevel(Integer newDiscountLevelToSet, Long userId);

    void updateBalanceAmount(BigDecimal balanceToAdd, Long userId);

    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);

    Optional<User> findByLoginAndPassword(String login, String password);

    List<Review> findReviewsByUserLogin(String login);
}