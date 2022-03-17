package com.ordjoy.model.dto;

import com.ordjoy.model.entity.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTrackOrderDto implements Serializable {

    private Long id;
    private BigDecimal price;
    private OrderStatus status;
    private UserDto user;
    private TrackDto track;
}