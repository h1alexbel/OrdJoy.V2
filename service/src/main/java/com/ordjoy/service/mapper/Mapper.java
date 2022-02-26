package com.ordjoy.service.mapper;

@FunctionalInterface
public interface Mapper<F, T> {

    T mapFrom(F objectFrom);
}