package com.ordjoy.database.repository.user;

import com.ordjoy.database.model.review.Review;
import com.ordjoy.database.model.review.Review_;
import com.ordjoy.database.model.user.User;
import com.ordjoy.database.model.user.User_;
import com.ordjoy.database.repository.AbstractGenericCRUDRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl extends AbstractGenericCRUDRepository<User, Long>
        implements UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void updateDiscountLevel(Integer newDiscountLevelToSet, Long userId) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("update User u set u.userData.discountPercentageLevel = :discountPercentageLevel")
                .setParameter("discountPercentageLevel", newDiscountLevelToSet)
                .executeUpdate();
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select u from User u where u.login = :login", User.class)
                .setParameter("login", login)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select u from User u where u.login = :login and u.password = :password",
                        User.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<Review> findReviewsByUserLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Review> criteria = cb.createQuery(Review.class);
        Root<Review> root = criteria.from(Review.class);
        Join<Review, User> userJoin = root.join(Review_.user);
        criteria.select(root)
                .where(cb.equal(userJoin.get(User_.login), login));
        return session.createQuery(criteria).getResultList();
    }
}