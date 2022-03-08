package com.ordjoy.service.service.order;

import com.ordjoy.database.model.order.OrderStatus;
import com.ordjoy.database.model.order.UserTrackOrder;
import com.ordjoy.service.dto.UserTrackOrderDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<UserTrackOrderDto> listOrders();

    UserTrackOrderDto makeOrder(UserTrackOrder order);

    Optional<UserTrackOrderDto> findOrderById(Long orderId);

    void updateOrder(UserTrackOrder userTrackOrder);

    void updateOrderPrice(BigDecimal price, Long orderId);

    void updateOrderStatus(OrderStatus status, Long orderId);

    void subtractBalanceFromUser(BigDecimal orderCost, Long userId);

    BigDecimal calculateOrderPrice(Long orderId);

    void deleteOrder(UserTrackOrder order);

    List<UserTrackOrderDto> findOrdersByPrice(BigDecimal price);

    List<UserTrackOrderDto> findOrdersByUserId(Long userId);

    List<UserTrackOrderDto> findOrdersByUserEmail(String email);

    List<UserTrackOrderDto> findOrdersByUserLogin(String login);

    List<UserTrackOrderDto> findOrdersByTrackId(Long trackId);

    List<UserTrackOrderDto> findOrdersByTrackTitle(String title);

    List<UserTrackOrderDto> findOrdersByStatus(OrderStatus status);
}