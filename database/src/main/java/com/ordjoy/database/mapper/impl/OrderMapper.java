package com.ordjoy.database.mapper.impl;

import com.ordjoy.database.dto.OrderDto;
import com.ordjoy.database.mapper.Mapper;
import com.ordjoy.database.model.UserTrackOrder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderMapper implements Mapper<UserTrackOrder, OrderDto> {

    private static final OrderMapper INSTANCE = new OrderMapper();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final TrackMapper trackMapper = TrackMapper.getInstance();

    @Override
    public OrderDto mapFrom(UserTrackOrder order) {
        return OrderDto.builder()
                .id(order.getId())
                .price(order.getPrice())
                .status(order.getStatus())
                .user(userMapper.mapFrom(order.getUser()))
                .track(trackMapper.mapFrom(order.getTrack()))
                .build();
    }

    public static OrderMapper getInstance() {
        return INSTANCE;
    }
}