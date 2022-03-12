package com.ordjoy.service.mapper;

@Deprecated(since = "1.2")
@FunctionalInterface
public interface Mapper<F, T> {

    T mapFrom(F objectFrom);
}