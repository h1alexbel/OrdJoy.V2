package com.ordjoy.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public enum Role {
    USER(Set.of(Permission.READ)),
    ADMIN(Set.of(Permission.READ, Permission.WRITE, Permission.SOFT_DELETE)),
    DBA(Set.of(Permission.READ, Permission.WRITE, Permission.HARD_DELETE, Permission.BACK_UP));

    private final Set<Permission> permissions;
}