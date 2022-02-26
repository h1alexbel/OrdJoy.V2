package com.ordjoy.database.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
    READ("user:read"), WRITE("user:write");

    private final String permissionName;
}