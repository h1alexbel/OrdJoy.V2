package com.ordjoy.database.repository.order;

import com.ordjoy.database.model.BaseEntity_;
import com.ordjoy.database.model.order.OrderStatus;
import com.ordjoy.database.model.order.UserTrackOrder;
import com.ordjoy.database.model.order.UserTrackOrder_;
import com.ordjoy.database.model.track.Track;
import com.ordjoy.database.model.track.Track_;
import com.ordjoy.database.model.user.User;
import com.ordjoy.database.model.user.User_;
import com.ordjoy.database.repository.AbstractGenericCRUDRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends AbstractGenericCRUDRepository<UserTrackOrder, Long>
        implements OrderRepository {

    @Override
    public void updateStatus(OrderStatus status, Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("update UserTrackOrder o set o.status = :status" +
                            " where o.id = :orderId")
                .setParameter("orderId", id)
                .setParameter("status", status)
                .executeUpdate();
    }

    @Override
    public void updatePrice(BigDecimal price, Long orderId) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("update UserTrackOrder o set o.price = :price" +
                            " where o.id = :orderId")
                .setParameter("price", price)
                .setParameter("orderId", orderId)
                .executeUpdate();
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