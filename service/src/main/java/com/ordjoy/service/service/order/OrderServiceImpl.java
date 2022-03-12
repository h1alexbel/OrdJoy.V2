package com.ordjoy.service.service.order;

import com.ordjoy.database.model.order.OrderStatus;
import com.ordjoy.database.model.order.UserTrackOrder;
import com.ordjoy.database.repository.order.OrderRepository;
import com.ordjoy.service.dto.AlbumDto;
import com.ordjoy.service.dto.TrackDto;
import com.ordjoy.service.dto.UserDto;
import com.ordjoy.service.dto.UserPersonalInfo;
import com.ordjoy.service.dto.UserTrackOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class OrderServiceImpl implements OrderService {

    private static final Integer STANDART_DISCOUNT_PERCENTAGE_LEVEL = 0;
    private static final int PERCENTAGE_AMOUNT = 100;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<UserTrackOrderDto> listOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapOrderToDto)
                .toList();
    }

    @Transactional
    @Override
    public UserTrackOrderDto makeOrder(UserTrackOrder order) {
        UserTrackOrder savedOrder = orderRepository.add(order);
        return mapOrderToDto(savedOrder);
    }

    @Override
    public Optional<UserTrackOrderDto> findOrderById(Long orderId) {
        if (orderId != null) {
            return orderRepository.findById(orderId).stream()
                    .map(this::mapOrderToDto)
                    .findFirst();
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void updateOrder(UserTrackOrder userTrackOrder) {
        if (userTrackOrder != null) {
            orderRepository.update(userTrackOrder);
        }
    }

    @Transactional
    @Override
    public void updateOrderPrice(BigDecimal price, Long orderId) {
        if (price != null && orderId != null) {
            orderRepository.updatePrice(price, orderId);
        }
    }

    @Transactional
    @Override
    public void updateOrderStatus(OrderStatus status, Long orderId) {
        if (status != null && orderId != null) {
            orderRepository.updateStatus(status, orderId);
        }
    }

    @Transactional
    @Override
    public void subtractBalanceFromUser(BigDecimal orderCost, Long userId) {
        if (orderCost != null && userId != null) {
            orderRepository.subtractBalance(orderCost, userId);
        }
    }

    @Override
    public BigDecimal calculateOrderPrice(Long orderId) {
        BigDecimal price = null;
        if (orderId != null) {
            Optional<UserTrackOrder> maybeOrder = orderRepository.findById(orderId);
            if (maybeOrder.isPresent()) {
                UserTrackOrder order = maybeOrder.get();
                int value = order.getPrice().intValue();
                Integer discountPercentageLevel = order.getUser()
                        .getUserData()
                        .getDiscountPercentageLevel();
                if (discountPercentageLevel > STANDART_DISCOUNT_PERCENTAGE_LEVEL) {
                    int discount = (value * discountPercentageLevel / PERCENTAGE_AMOUNT);
                    price = new BigDecimal(value - discount);
                }
            }
        }
        return price;
    }

    @Transactional
    @Override
    public void deleteOrder(UserTrackOrder order) {
        if (order != null) {
            orderRepository.delete(order);
        }
    }

    @Override
    public List<UserTrackOrderDto> findOrdersByPrice(BigDecimal price) {
        if (price != null) {
            return orderRepository.findOrdersByPrice(price).stream()
                    .map(this::mapOrderToDto)
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<UserTrackOrderDto> findOrdersByUserId(Long userId) {
        if (userId != null) {
            return orderRepository.findOrdersByUserId(userId).stream()
                    .map(this::makeOrder)
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<UserTrackOrderDto> findOrdersByUserEmail(String email) {
        if (email != null) {
            return orderRepository.findOrdersByUserEmail(email).stream()
                    .map(this::mapOrderToDto)
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<UserTrackOrderDto> findOrdersByUserLogin(String login) {
        if (login != null) {
            return orderRepository.findOrdersByUserLogin(login).stream()
                    .map(this::mapOrderToDto)
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<UserTrackOrderDto> findOrdersByTrackId(Long trackId) {
        if (trackId != null) {
            return orderRepository.findOrdersByTrackId(trackId).stream()
                    .map(this::makeOrder)
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<UserTrackOrderDto> findOrdersByTrackTitle(String title) {
        if (title != null) {
            return orderRepository.findOrdersByTrackTitle(title).stream()
                    .map(this::mapOrderToDto)
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<UserTrackOrderDto> findOrdersByStatus(OrderStatus status) {
        if (status != null) {
            return orderRepository.findOrdersByStatus(status).stream()
                    .map(this::mapOrderToDto)
                    .toList();
        }
        return Collections.emptyList();
    }

    private UserTrackOrderDto mapOrderToDto(UserTrackOrder order) {
        return UserTrackOrderDto.builder()
                .id(order.getId())
                .price(order.getPrice())
                .status(order.getStatus())
                .track(TrackDto.builder()
                        .id(order.getTrack().getId())
                        .title(order.getTrack().getTitle())
                        .url(order.getTrack().getUrl())
                        .album(AlbumDto.builder()
                                .id(order.getTrack().getAlbum().getId())
                                .title(order.getTrack().getAlbum().getTitle())
                                .build())
                        .build())
                .user(UserDto.builder()
                        .id(order.getUser().getId())
                        .login(order.getUser().getLogin())
                        .email(order.getUser().getEmail())
                        .personalInfo(UserPersonalInfo.builder()
                                .accountBalance(order.getUser().getUserData()
                                        .getAccountBalance())
                                .discountPercentageLevel(order.getUser().getUserData()
                                        .getDiscountPercentageLevel())
                                .build())
                        .build())
                .build();
    }
}