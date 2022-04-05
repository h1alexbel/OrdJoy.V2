package com.ordjoy.model.service;

import com.ordjoy.model.dto.UserTrackOrderDto;
import com.ordjoy.model.entity.order.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService extends GenericCRUDService<UserTrackOrderDto, Long> {

    void updateOrderStatus(OrderStatus status, Long id);

    BigDecimal calculateOrderPrice(Long orderId);

    void deleteOrder(Long orderId);

    List<UserTrackOrderDto> findOrdersByPrice(BigDecimal price);

    List<UserTrackOrderDto> findOrdersByUserId(Long userId);

    List<UserTrackOrderDto> findOrdersByUserEmail(String email);

    List<UserTrackOrderDto> findOrdersByUserLogin(String login);

    List<UserTrackOrderDto> findOrdersByTrackId(Long trackId);

    List<UserTrackOrderDto> findOrdersByTrackTitle(String title);

    List<UserTrackOrderDto> findOrdersByStatus(OrderStatus status);
}