package com.ordjoy.database.repository.review;

import com.ordjoy.database.model.review.TrackReview;
import com.ordjoy.database.repository.GenericCRUDRepository;

import java.util.List;

public interface TrackReviewRepository extends GenericCRUDRepository<TrackReview, Long> {

    List<TrackReview> findTrackReviewsByTrackTitle(String title);

    List<TrackReview> findTrackReviewsByTrackId(Long trackId);

    List<TrackReview> findTrackReviewsByUserLogin(String login);

    List<TrackReview> findTrackReviewsByUserId(Long userId);
}