package com.ordjoy.model.service;

import com.ordjoy.model.dto.TrackReviewDto;

public interface TrackReviewService extends ReviewService<TrackReviewDto, Long> {

    Long getTrackReviewWithUserLoginPredicatePages(String login);
}