package com.ordjoy.model.repository.order;

import com.ordjoy.model.entity.BaseEntity_;
import com.ordjoy.model.entity.order.OrderStatus;
import com.ordjoy.model.entity.order.UserTrackOrder;
import com.ordjoy.model.entity.order.UserTrackOrder_;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.entity.track.Track_;
import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.entity.user.User_;
import com.ordjoy.model.repository.AbstractGenericCRUDRepository;
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
                .setParameter("price", price)
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
    public List<UserTrackOrder> findOrdersByUserEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserTrackOrder> criteria = cb.createQuery(UserTrackOrder.class);
        Root<UserTrackOrder> root = criteria.from(UserTrackOrder.class);
        Join<UserTrackOrder, User> userJoin = root.join(UserTrackOrder_.user);
        criteria.select(root)
                .where(cb.equal(userJoin.get(User_.email), email));
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<UserTrackOrder> findOrdersByUserLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserTrackOrder> criteria = cb.createQuery(UserTrackOrder.class);
        Root<UserTrackOrder> root = criteria.from(UserTrackOrder.class);
        Join<UserTrackOrder, User> userJoin = root.join(UserTrackOrder_.user);
        criteria.select(root)
                .where(cb.equal(userJoin.get(User_.login), login));
        return session.createQuery(criteria).getResultList();
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
    public List<UserTrackOrder> findOrdersByTrackTitle(String title) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserTrackOrder> criteria = cb.createQuery(UserTrackOrder.class);
        Root<UserTrackOrder> root = criteria.from(UserTrackOrder.class);
        Join<UserTrackOrder, Track> trackJoin = root.join(UserTrackOrder_.track);
        criteria.select(root)
                .where(cb.equal(trackJoin.get(Track_.title), title));
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<UserTrackOrder> findOrdersByStatus(OrderStatus status) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select o from UserTrackOrder o where o.status = :status",
                        UserTrackOrder.class)
                .setParameter("status", status)
                .getResultList();
    }
}