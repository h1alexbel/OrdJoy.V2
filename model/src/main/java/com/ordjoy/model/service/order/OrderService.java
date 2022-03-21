package com.ordjoy.model.service.order;

import com.ordjoy.model.dto.UserTrackOrderDto;
import com.ordjoy.model.entity.order.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<UserTrackOrderDto> listOrders();

    UserTrackOrderDto makeOrder(UserTrackOrderDto orderDto);

    Optional<UserTrackOrderDto> findOrderById(Long orderId);

    void updateOrder(UserTrackOrderDto userTrackOrderDto);

    void subtractBalanceFromUser(BigDecimal orderCost, Long userId);

    void updateOrderStatus(OrderStatus status, Long id);

    BigDecimal calculateOrderPrice(Long orderId);

    void deleteOrder(UserTrackOrderDto orderDto);

    List<UserTrackOrderDto> findOrdersByPrice(BigDecimal price);

    List<UserTrackOrderDto> findOrdersByUserId(Long userId);

    List<UserTrackOrderDto> findOrdersByUserEmail(String email);

    List<UserTrackOrderDto> findOrdersByUserLogin(String login);

    List<UserTrackOrderDto> findOrdersByTrackId(Long trackId);

    List<UserTrackOrderDto> findOrdersByTrackTitle(String title);

    List<UserTrackOrderDto> findOrdersByStatus(OrderStatus status);
}