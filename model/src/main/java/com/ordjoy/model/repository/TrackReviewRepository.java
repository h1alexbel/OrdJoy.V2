package com.ordjoy.model.repository;

import com.ordjoy.model.entity.review.TrackReview;

import java.util.List;

public interface TrackReviewRepository extends GenericCRUDRepository<TrackReview, Long> {

    List<TrackReview> findTrackReviewsByUserLogin(String login, int limit, int offset);

    List<TrackReview> findTrackReviewsByUserId(Long userId);

    Long getTrackReviewWithUserLoginPredicateRecords(String login);
}