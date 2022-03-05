package com.ordjoy.database.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
    READ("user:read"),
    WRITE("user:write"),
    BACK_UP("user:backup"),
    HARD_DELETE("user:delete");

    private final String permissionName;
}