package com.ordjoy.database.repository.review;

import com.ordjoy.database.model.review.TrackReview;

import java.util.List;

public interface TrackReviewRepository {

    List<TrackReview> findTrackReviewsByTrackTitle(String title);

    List<TrackReview> findTrackReviewsByTrackId(Long trackId);

    List<TrackReview> findTrackReviewsByUserLogin(String login);

    List<TrackReview> findTrackReviewsByUserId(Long userId);
}