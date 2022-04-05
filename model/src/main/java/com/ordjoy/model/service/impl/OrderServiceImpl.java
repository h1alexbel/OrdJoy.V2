package com.ordjoy.model.service.impl;

import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.dto.UserPersonalInfo;
import com.ordjoy.model.dto.UserTrackOrderDto;
import com.ordjoy.model.entity.order.OrderStatus;
import com.ordjoy.model.entity.order.UserTrackOrder;
import com.ordjoy.model.entity.track.Album;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.entity.user.UserData;
import com.ordjoy.model.repository.OrderRepository;
import com.ordjoy.model.service.OrderService;
import com.ordjoy.model.util.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
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
    public List<UserTrackOrderDto> list(int limit, int offset) {
        return orderRepository.findAll(limit, offset).stream()
                .map(this::mapOrderToDto)
                .toList();
    }

    @Transactional
    @Override
    public UserTrackOrderDto save(UserTrackOrderDto orderDto) {
        User user = User.builder()
                .login(orderDto.getUser().getLogin())
                .email(orderDto.getUser().getEmail())
                .userData(UserData.builder()
                        .accountBalance(orderDto.getUser().getPersonalInfo()
                                .getAccountBalance())
                        .discountPercentageLevel(orderDto.getUser().getPersonalInfo()
                                .getDiscountPercentageLevel())
                        .build())
                .build();
        UserTrackOrder orderToSave = UserTrackOrder.builder()
                .price(orderDto.getPrice())
                .status(OrderStatus.ACCEPTED)
                .track(Track.builder()
                        .title(orderDto.getTrack().getTitle())
                        .url(orderDto.getTrack().getUrl())
                        .album(Album.builder()
                                .title(orderDto.getTrack().getAlbum().getTitle())
                                .build())
                        .build())
                .user(user)
                .build();
        orderToSave.getTrack().getAlbum().setId(orderDto.getTrack().getAlbum().getId());
        orderToSave.getTrack().setId(orderDto.getTrack().getId());
        orderToSave.getUser().setId(orderDto.getUser().getId());
        user.addOrderToUser(orderToSave);
        UserTrackOrder savedOrder = orderRepository.add(orderToSave);
        log.debug(LoggingUtils.ORDER_WAS_CREATED_SERVICE, savedOrder);
        return mapOrderToDto(savedOrder);
    }

    @Override
    public Optional<UserTrackOrderDto> findById(Long orderId) {
        if (orderId != null) {
            return orderRepository.findById(orderId).stream()
                    .map(this::mapOrderToDto)
                    .findFirst();
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void update(UserTrackOrderDto userTrackOrderDto) {
        if (userTrackOrderDto != null) {
            orderRepository.update(mapEntityFromDto(userTrackOrderDto));
            log.debug(LoggingUtils.ORDER_WAS_UPDATED_SERVICE, userTrackOrderDto);
        }
    }

    @Transactional
    @Override
    public void updateOrderStatus(OrderStatus status, Long id) {
        if (status != null && id != null) {
            Optional<UserTrackOrder> maybeOrder = orderRepository.findById(id);
            maybeOrder.ifPresent(order -> {
                orderRepository.updateOrderStatus(status, order.getId());
                log.debug(LoggingUtils.ORDER_STATUS_WAS_UPDATED_SERVICE, order);
            });
        }
    }

    @Override
    public BigDecimal calculateOrderPrice(Long orderId) {
        BigDecimal price = null;
        if (orderId != null) {
            Optional<UserTrackOrder> maybeOrder = orderRepository.findById(orderId);
            if (maybeOrder.isPresent()) {
                UserTrackOrder order = maybeOrder.get();
                price = order.getPrice();
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
        log.debug(LoggingUtils.ORDER_PRICE_WAS_CALCULATED, price);
        return price;
    }

    @Transactional
    @Override
    public void deleteOrder(Long orderId) {
        if (orderId != null) {
            Optional<UserTrackOrder> maybeOrder = orderRepository.findById(orderId);
            maybeOrder.ifPresent(order -> {
                orderRepository.delete(order);
                log.debug(LoggingUtils.ORDER_WAS_DELETED_SERVICE, order);
            });
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
                    .map(this::mapOrderToDto)
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
                    .map(this::mapOrderToDto)
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
        UserDto user = UserDto.builder()
                .id(order.getUser().getId())
                .login(order.getUser().getLogin())
                .email(order.getUser().getEmail())
                .personalInfo(UserPersonalInfo.builder()
                        .accountBalance(order.getUser().getUserData()
                                .getAccountBalance())
                        .discountPercentageLevel(order.getUser().getUserData()
                                .getDiscountPercentageLevel())
                        .build())
                .build();
        order.getUser().addOrderToUser(order);
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
                .user(user)
                .build();
    }

    private UserTrackOrder mapEntityFromDto(UserTrackOrderDto orderDto) {
        User user = User.builder()
                .login(orderDto.getUser().getLogin())
                .email(orderDto.getUser().getEmail())
                .userData(UserData.builder()
                        .accountBalance(orderDto.getUser().getPersonalInfo()
                                .getAccountBalance())
                        .discountPercentageLevel(orderDto.getUser().getPersonalInfo()
                                .getDiscountPercentageLevel())
                        .build())
                .build();
        user.setId(orderDto.getUser().getId());
        Album album = Album.builder()
                .title(orderDto.getTrack().getAlbum().getTitle())
                .build();
        album.setId(orderDto.getTrack().getAlbum().getId());
        Track track = Track.builder()
                .title(orderDto.getTrack().getTitle())
                .url(orderDto.getTrack().getUrl())
                .album(album)
                .build();
        track.setId(orderDto.getTrack().getId());
        UserTrackOrder order = UserTrackOrder.builder()
                .price(orderDto.getPrice())
                .status(orderDto.getStatus())
                .track(track)
                .user(user)
                .build();
        order.setId(orderDto.getId());
        return order;
    }
}