package com.ordjoy.database.dto;

import com.ordjoy.database.model.OrderStatus;
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
public class OrderDto implements Serializable {

    private Long id;
    private BigDecimal price;
    private OrderStatus status;
    private UserDto user;
    private TrackDto track;
}