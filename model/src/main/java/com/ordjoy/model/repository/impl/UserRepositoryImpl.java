package com.ordjoy.model.repository.impl;

import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.repository.UserRepository;
import com.ordjoy.model.util.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepositoryImpl extends AbstractGenericCRUDRepository<User, Long>
        implements UserRepository {

    private static final String LOGIN_PARAM = "login";
    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";

    @Override
    public void subtractBalance(BigDecimal orderCost, Long userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, userId);
        user.getUserData().setAccountBalance(user.getUserData().getAccountBalance()
                .subtract(orderCost));
        session.update(user);
        log.debug(LoggingUtils.USER_BALANCE_WAS_SUBTRACTED_REPO, orderCost, userId);
    }

    @Override
    public void updateDiscountLevel(Integer newDiscountLevelToSet, Long userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, userId);
        user.getUserData().setDiscountPercentageLevel(newDiscountLevelToSet);
        session.update(user);
        log.debug(LoggingUtils.USER_DISCOUNT_PERCENTAGE_LEVEL_WAS_UPDATED_REPO, newDiscountLevelToSet);
    }

    @Override
    public void updateBalanceAmount(BigDecimal balanceToAdd, Long userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, userId);
        user.getUserData().setAccountBalance(user.getUserData().getAccountBalance()
                .add(balanceToAdd));
        session.update(user);
        log.debug(LoggingUtils.USER_ACCOUNT_BALANCE_WAS_UPDATED_REPO, balanceToAdd);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        return session
                .createQuery("select u from User u where u.login = :login", User.class)
                .setParameter(LOGIN_PARAM, login)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        return session
                .createQuery("select u from User u where u.email = :email", User.class)
                .setParameter(EMAIL_PARAM, email)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }
}