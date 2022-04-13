package com.ordjoy.model.service;

import com.ordjoy.model.dto.AlbumReviewDto;

public interface AlbumReviewService extends ReviewService<AlbumReviewDto, Long> {

    Long getAlbumReviewWithUserLoginPredicatePages(String login);
}