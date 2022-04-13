package com.ordjoy.model.service;

import com.ordjoy.model.dto.MixReviewDto;

public interface MixReviewService extends ReviewService<MixReviewDto, Long> {

    Long getMixReviewWithUserLoginPredicatePages(String login);
}