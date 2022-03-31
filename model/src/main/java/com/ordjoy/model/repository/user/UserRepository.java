package com.ordjoy.model.repository.user;

import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.repository.GenericCRUDRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserRepository extends GenericCRUDRepository<User, Long> {

    void subtractBalance(BigDecimal cost, Long userId);

    void updateDiscountLevel(Integer newDiscountLevelToSet, Long userId);

    void updateBalanceAmount(BigDecimal balanceToAdd, Long userId);

    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);

    Optional<User> findByLoginAndPassword(String login, String password);
}