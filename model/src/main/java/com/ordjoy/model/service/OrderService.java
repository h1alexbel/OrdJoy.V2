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

    List<UserTrackOrderDto> findOrdersByUserEmail(String email, int limit, int offset);

    List<UserTrackOrderDto> findOrdersByUserLogin(String login, int limit, int offset);

    List<UserTrackOrderDto> findOrdersByTrackId(Long trackId);

    List<UserTrackOrderDto> findOrdersByTrackTitle(String title, int limit, int offset);

    List<UserTrackOrderDto> findOrdersByStatus(OrderStatus status, int limit, int offset);

    Long getOrderWithUserEmailPredicatePages(String email);

    Long getOrderWithUserLoginPredicatePages(String login);

    Long getOrderWithTrackTitlePredicatePages(String trackTitle);

    Long getOrderWithStatusPredicatePages(OrderStatus status);
}