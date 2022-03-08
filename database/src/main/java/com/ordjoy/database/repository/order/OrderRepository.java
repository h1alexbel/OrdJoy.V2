package com.ordjoy.database.repository.order;

import com.ordjoy.database.model.order.OrderStatus;
import com.ordjoy.database.model.order.UserTrackOrder;
import com.ordjoy.database.repository.GenericCRUDRepository;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends GenericCRUDRepository<UserTrackOrder, Long> {

    void updateStatus(OrderStatus status, Long id);

    void updatePrice(BigDecimal price, Long id);

    void subtractBalance(BigDecimal cost, Long userId);

    List<UserTrackOrder> findOrdersByPrice(BigDecimal price);

    List<UserTrackOrder> findOrdersByUserId(Long userId);

    List<UserTrackOrder> findOrdersByUserEmail(String email);

    List<UserTrackOrder> findOrdersByUserLogin(String login);

    List<UserTrackOrder> findOrdersByTrackId(Long trackId);

    List<UserTrackOrder> findOrdersByTrackTitle(String title);

    List<UserTrackOrder> findOrdersByStatus(OrderStatus status);
}