package com.ordjoy.service.mapper.impl;

import com.ordjoy.database.model.order.UserTrackOrder;
import com.ordjoy.service.dto.UserTrackOrderDto;
import com.ordjoy.service.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Deprecated(since = "1.2")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderMapper implements Mapper<UserTrackOrder, UserTrackOrderDto> {

    private static final OrderMapper INSTANCE = new OrderMapper();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final TrackMapper trackMapper = TrackMapper.getInstance();

    @Override
    public UserTrackOrderDto mapFrom(UserTrackOrder order) {
        return UserTrackOrderDto.builder()
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