package com.ordjoy.database.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {
    ACCEPTED,
    IN_PROGRESS,
    CANCELLED
}