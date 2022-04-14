package com.ordjoy.model.repository;

import com.ordjoy.model.entity.order.OrderStatus;
import com.ordjoy.model.entity.order.UserTrackOrder;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends GenericCRUDRepository<UserTrackOrder, Long> {

    /**
     * Updates {@link UserTrackOrder} {@link OrderStatus}
     *
     * @param orderStatus new Status to set
     * @param id          UserTrackOrder Identifier
     */
    void updateOrderStatus(OrderStatus orderStatus, Long id);

    /**
     * Finds List of {@link UserTrackOrder} from DB by its price
     *
     * @param price UserTrackOrder price
     * @return List of UserTrackOrders
     */
    List<UserTrackOrder> findOrdersByPrice(BigDecimal price);

    /**
     * Finds List of {@link UserTrackOrder} from DB by User id
     *
     * @param userId User's Identifier
     * @return List of UserTrackOrders
     */
    List<UserTrackOrder> findOrdersByUserId(Long userId);

    /**
     * Finds List of {@link UserTrackOrder} from DB by User email
     *
     * @param email  unique User's email
     * @param limit  LIMIT of UserTrackOrders
     * @param offset OFFSET of UserTrackOrders
     * @return List of UserTrackOrders
     */
    List<UserTrackOrder> findOrdersByUserEmail(String email, int limit, int offset);

    /**
     * Finds List of {@link UserTrackOrder} from DB by User login
     *
     * @param login  unique User's login
     * @param limit  LIMIT of UserTrackOrders
     * @param offset OFFSET of UserTrackOrders
     * @return List of UserTrackOrders
     */
    List<UserTrackOrder> findOrdersByUserLogin(String login, int limit, int offset);

    /**
     * Finds List of {@link UserTrackOrder} from DB by Track id
     *
     * @param trackId Track's Identifier
     * @return List of UserTrackOrders
     */
    List<UserTrackOrder> findOrdersByTrackId(Long trackId);

    /**
     * Finds List of {@link UserTrackOrder} from DB by Track title
     *
     * @param title  unique Track's title
     * @param limit  LIMIT of UserTrackOrder
     * @param offset OFFSET of UserTrackOrder
     * @return List of UserTrackOrders
     */
    List<UserTrackOrder> findOrdersByTrackTitle(String title, int limit, int offset);

    /**
     * Finds List of {@link UserTrackOrder} from DB by its status
     *
     * @param status UserTrackOrder status
     * @param limit  LIMIT of UserTrackOrder
     * @param offset OFFSET of UserTrackOrder
     * @return List of UserTrackOrders
     */
    List<UserTrackOrder> findOrdersByStatus(OrderStatus status, int limit, int offset);

    /**
     * Finds count of all active UserTrackOrders from DB which have User's email like in param
     *
     * @param email unique User's email
     * @return Long value of records count
     */
    Long getOrderWithUserEmailPredicateRecords(String email);

    /**
     * Finds count of all active UserTrackOrders from DB which have User's login like in param
     *
     * @param login unique User's login
     * @return Long value of records count
     */
    Long getOrderWithUserLoginPredicateRecords(String login);

    /**
     * Finds count of all active UserTrackOrders from DB which have Track's trackTitle like in param
     *
     * @param trackTitle unique Track's trackTitle
     * @return Long value of records count
     */
    Long getOrderWithTrackTitlePredicateRecords(String trackTitle);

    /**
     * Finds count of all active UserTrackOrders from DB which have status like in param
     *
     * @param status UserTrackOrder's status
     * @return Long value of records count
     */
    Long getOrderWithStatusPredicateRecords(OrderStatus status);
}