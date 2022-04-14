package com.ordjoy.model.service;

import com.ordjoy.model.dto.AlbumReviewDto;

public interface AlbumReviewService extends ReviewService<AlbumReviewDto, Long> {

    /**
     * Finds count of pages that store AlbumReviews from DB which have userLogin like in param
     *
     * @param login unique UserDto login
     * @return Long value of pages count
     */
    Long getAlbumReviewWithUserLoginPredicatePages(String login);
}