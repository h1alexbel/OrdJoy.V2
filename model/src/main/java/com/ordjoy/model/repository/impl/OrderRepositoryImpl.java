package com.ordjoy.model.repository.impl;

import com.ordjoy.model.entity.BaseEntity_;
import com.ordjoy.model.entity.order.OrderStatus;
import com.ordjoy.model.entity.order.UserTrackOrder;
import com.ordjoy.model.entity.order.UserTrackOrder_;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.entity.track.Track_;
import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.entity.user.User_;
import com.ordjoy.model.repository.OrderRepository;
import com.ordjoy.model.util.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Repository
public class OrderRepositoryImpl extends AbstractGenericCRUDRepository<UserTrackOrder, Long>
        implements OrderRepository {

    private static final String PRICE_PARAM = "price";
    private static final String STATUS_PARAM = "status";
    private static final String EMAIL_PARAM = "email";
    private static final String LOGIN_PARAM = "login";
    private static final String TRACK_TITLE_PARAM = "trackTitle";

    @Override
    public void updateOrderStatus(OrderStatus orderStatus, Long id) {
        Session session = sessionFactory.getCurrentSession();
        UserTrackOrder userTrackOrder = session.get(UserTrackOrder.class, id);
        userTrackOrder.setStatus(orderStatus);
        session.update(userTrackOrder);
        log.debug(LoggingUtils.ORDER_STATUS_WAS_UPDATED_REPO, orderStatus, id);
    }

    @Override
    public List<UserTrackOrder> findOrdersByPrice(BigDecimal price) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select o from UserTrackOrder o where o.price = :price",
                        UserTrackOrder.class)
                .setParameter(PRICE_PARAM, price)
                .getResultList();
    }

    @Override
    public List<UserTrackOrder> findOrdersByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserTrackOrder> criteria = cb.createQuery(UserTrackOrder.class);
        Root<UserTrackOrder> root = criteria.from(UserTrackOrder.class);
        Join<UserTrackOrder, User> userJoin = root.join(UserTrackOrder_.user);
        criteria.select(root)
                .where(cb.equal(userJoin.get(BaseEntity_.id), userId));
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<UserTrackOrder> findOrdersByUserEmail(String email, int limit, int offset) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserTrackOrder> criteria = cb.createQuery(UserTrackOrder.class);
        Root<UserTrackOrder> root = criteria.from(UserTrackOrder.class);
        Join<UserTrackOrder, User> userJoin = root.join(UserTrackOrder_.user);
        criteria.select(root)
                .where(cb.equal(userJoin.get(User_.email), email))
                .orderBy(cb.desc(root.get(BaseEntity_.id)));
        return session.createQuery(criteria)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public List<UserTrackOrder> findOrdersByUserLogin(String login, int limit, int offset) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserTrackOrder> criteria = cb.createQuery(UserTrackOrder.class);
        Root<UserTrackOrder> root = criteria.from(UserTrackOrder.class);
        Join<UserTrackOrder, User> userJoin = root.join(UserTrackOrder_.user);
        criteria.select(root)
                .where(cb.equal(userJoin.get(User_.login), login))
                .orderBy(cb.desc(root.get(BaseEntity_.id)));
        return session.createQuery(criteria)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public List<UserTrackOrder> findOrdersByTrackId(Long trackId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserTrackOrder> criteria = cb.createQuery(UserTrackOrder.class);
        Root<UserTrackOrder> root = criteria.from(UserTrackOrder.class);
        Join<UserTrackOrder, Track> trackJoin = root.join(UserTrackOrder_.track);
        criteria.select(root)
                .where(cb.equal(trackJoin.get(BaseEntity_.id), trackId));
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<UserTrackOrder> findOrdersByTrackTitle(String title, int limit, int offset) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserTrackOrder> criteria = cb.createQuery(UserTrackOrder.class);
        Root<UserTrackOrder> root = criteria.from(UserTrackOrder.class);
        Join<UserTrackOrder, Track> trackJoin = root.join(UserTrackOrder_.track);
        criteria.select(root)
                .where(cb.equal(trackJoin.get(Track_.title), title))
                .orderBy(cb.desc(root.get(BaseEntity_.id)));
        return session.createQuery(criteria)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public List<UserTrackOrder> findOrdersByStatus(OrderStatus status, int limit, int offset) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select o from UserTrackOrder o" +
                                   " where o.status = :status order by o.id desc",
                        UserTrackOrder.class)
                .setParameter(STATUS_PARAM, status)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Long getOrderWithUserEmailPredicateRecords(String email) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(o) from UserTrackOrder o" +
                                   " join o.user u where u.email=:email", Long.class)
                .setParameter(EMAIL_PARAM, email)
                .getSingleResult();
    }

    @Override
    public Long getOrderWithUserLoginPredicateRecords(String login) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(o) from UserTrackOrder o" +
                                   " join o.user u where u.login=:login", Long.class)
                .setParameter(LOGIN_PARAM, login)
                .getSingleResult();
    }

    @Override
    public Long getOrderWithTrackTitlePredicateRecords(String trackTitle) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(o) from UserTrackOrder o" +
                                   " join o.track t where t.title=:trackTitle", Long.class)
                .setParameter(TRACK_TITLE_PARAM, trackTitle)
                .getSingleResult();
    }

    @Override
    public Long getOrderWithStatusPredicateRecords(OrderStatus status) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(o) from UserTrackOrder o" +
                                   " where o.status=:status", Long.class)
                .setParameter(STATUS_PARAM, status)
                .getSingleResult();
    }
}