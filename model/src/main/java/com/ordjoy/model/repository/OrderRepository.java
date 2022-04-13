package com.ordjoy.model.repository;

import com.ordjoy.model.entity.order.OrderStatus;
import com.ordjoy.model.entity.order.UserTrackOrder;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends GenericCRUDRepository<UserTrackOrder, Long> {

    void updateOrderStatus(OrderStatus orderStatus, Long id);

    List<UserTrackOrder> findOrdersByPrice(BigDecimal price);

    List<UserTrackOrder> findOrdersByUserId(Long userId);

    List<UserTrackOrder> findOrdersByUserEmail(String email, int limit, int offset);

    List<UserTrackOrder> findOrdersByUserLogin(String login, int limit, int offset);

    List<UserTrackOrder> findOrdersByTrackId(Long trackId);

    List<UserTrackOrder> findOrdersByTrackTitle(String title, int limit, int offset);

    List<UserTrackOrder> findOrdersByStatus(OrderStatus status, int limit, int offset);

    Long getOrderWithUserEmailPredicateRecords(String email);

    Long getOrderWithUserLoginPredicateRecords(String login);

    Long getOrderWithTrackTitlePredicateRecords(String trackTitle);

    Long getOrderWithStatusPredicateRecords(OrderStatus status);
}