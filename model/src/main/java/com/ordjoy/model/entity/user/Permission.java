package com.ordjoy.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
    READ("user:read"),
    WRITE("user:write"),
    BACK_UP("user:backup"),
    SOFT_DELETE("user:delete"),
    HARD_DELETE("user:delete -h");

    private final String permissionName;
}