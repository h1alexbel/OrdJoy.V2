package com.ordjoy.model.service;

import com.ordjoy.model.dto.MixReviewDto;

public interface MixReviewService extends ReviewService<MixReviewDto, Long> {

    /**
     * Finds count of pages that store MixReviews from DB which have userLogin like in param
     *
     * @param login unique UserDto login
     * @return Long value of pages count
     */
    Long getMixReviewWithUserLoginPredicatePages(String login);
}