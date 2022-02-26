package com.ordjoy.database.model.order;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {
    ACCEPTED,
    IN_PROGRESS,
    CANCELLED
}