package com.ordjoy.model.repository;

import com.ordjoy.model.entity.review.TrackReview;

import java.util.List;

public interface TrackReviewRepository extends GenericCRUDRepository<TrackReview, Long> {

    List<TrackReview> findTrackReviewsByUserLogin(String login);

    List<TrackReview> findTrackReviewsByUserId(Long userId);
}