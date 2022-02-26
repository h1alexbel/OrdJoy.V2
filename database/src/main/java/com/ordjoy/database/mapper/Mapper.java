package com.ordjoy.database.mapper;

@FunctionalInterface
public interface Mapper<F, T> {

    T mapFrom(F objectFrom);
}