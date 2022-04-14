package com.ordjoy.model.service;

import com.ordjoy.model.dto.TrackReviewDto;

public interface TrackReviewService extends ReviewService<TrackReviewDto, Long> {

    /**
     * Finds count of pages that store TrackReviews from DB which have userLogin like in param
     *
     * @param login unique UserDto login
     * @return Long value of pages count
     */
    Long getTrackReviewWithUserLoginPredicatePages(String login);
}