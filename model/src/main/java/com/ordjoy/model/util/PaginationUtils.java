package com.ordjoy.model.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PaginationUtils {

    private static final int DEFAULT_LIMIT = 5;

    public static Long collectToPages(Long records) {
        return (records / DEFAULT_LIMIT);
    }
}