package com.ordjoy.model.repository.user;

import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.log.LoggingUtils;
import com.ordjoy.model.repository.AbstractGenericCRUDRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepositoryImpl extends AbstractGenericCRUDRepository<User, Long>
        implements UserRepository {

    @Override
    public void updateDiscountLevel(Integer newDiscountLevelToSet, Long userId) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("update User u" +
                            " set u.userData.discountPercentageLevel = :discountPercentageLevel" +
                            " where u.id = :userId")
                .setParameter("discountPercentageLevel", newDiscountLevelToSet)
                .setParameter("userId", userId)
                .executeUpdate();
        log.debug(LoggingUtils.USER_DISCOUNT_PERCENTAGE_LEVEL_WAS_UPDATED, newDiscountLevelToSet);
    }

    @Override
    public void updateBalanceAmount(BigDecimal balanceToAdd, Long userId) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("update User u " +
                            "set u.userData.accountBalance = u.userData.accountBalance + :amount" +
                            " where u.id = :userId")
                .setParameter("amount", balanceToAdd)
                .setParameter("userId", userId)
                .executeUpdate();
        log.debug(LoggingUtils.USER_ACCOUNT_BALANCE_WAS_UPDATED, balanceToAdd);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        return session
                .createQuery("select u from User u where u.login = :login", User.class)
                .setParameter("login", login)
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
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sessionFactory.getCurrentSession();
        Optional<User> user = session
                .createQuery("select u from User u where u.login = :login and u.password = :password",
                        User.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
        log.debug(LoggingUtils.USER_WAS_FOUND_BY_LOGIN_AND_PASSWORD, user);
        return user;
    }
}