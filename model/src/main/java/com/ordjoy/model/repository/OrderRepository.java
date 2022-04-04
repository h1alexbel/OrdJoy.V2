package com.ordjoy.model.repository;

import com.ordjoy.model.entity.order.OrderStatus;
import com.ordjoy.model.entity.order.UserTrackOrder;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends GenericCRUDRepository<UserTrackOrder, Long> {

    void updateOrderStatus(OrderStatus orderStatus, Long id);

    List<UserTrackOrder> findOrdersByPrice(BigDecimal price);

    List<UserTrackOrder> findOrdersByUserId(Long userId);

    List<UserTrackOrder> findOrdersByUserEmail(String email);

    List<UserTrackOrder> findOrdersByUserLogin(String login);

    List<UserTrackOrder> findOrdersByTrackId(Long trackId);

    List<UserTrackOrder> findOrdersByTrackTitle(String title);

    List<UserTrackOrder> findOrdersByStatus(OrderStatus status);
}