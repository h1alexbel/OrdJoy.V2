package com.ordjoy.model.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PaginationUtils {

    private static final int DEFAULT_LIMIT = 5;

    /**
     * Collects all records to the pages
     *
     * @param records DB from records
     * @return count of pages for pagination
     */
    public static Long collectToPages(Long records) {
        return (records / DEFAULT_LIMIT);
    }
}