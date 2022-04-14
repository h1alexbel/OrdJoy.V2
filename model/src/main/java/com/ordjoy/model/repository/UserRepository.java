package com.ordjoy.model.repository;

import com.ordjoy.model.entity.user.User;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserRepository extends GenericCRUDRepository<User, Long> {

    /**
     * Update User's balance amount by subtracting order cost
     *
     * @param cost   {@link com.ordjoy.model.entity.order.UserTrackOrder} cost
     * @param userId User Identifier account balance whom will be subtracted from actual balance
     */
    void subtractBalance(BigDecimal cost, Long userId);

    /**
     * Updates User's discount percentage level
     *
     * @param newDiscountLevelToSet new Discount percentage level that will be set
     * @param userId                User Identifier discount percentage level whom will be updated
     */
    void updateDiscountLevel(Integer newDiscountLevelToSet, Long userId);

    /**
     * Updates User's balance amount by adding new value of account balance
     *
     * @param balanceToAdd Balance amount that be added
     * @param userId       User Identifier balance whom will be updated
     */
    void updateBalanceAmount(BigDecimal balanceToAdd, Long userId);

    /**
     * Finds Persistent User from the DB by its login
     *
     * @param login unique User login
     * @return {@link Optional} of Persistent User if present or empty {@link Optional}
     */
    Optional<User> findByLogin(String login);

    /**
     * Finds Persistent User from the DB by its email
     *
     * @param email unique User email
     * @return {@link Optional} of Persistent User if present or empty {@link Optional}
     */
    Optional<User> findByEmail(String email);
}