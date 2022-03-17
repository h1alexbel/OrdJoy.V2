package com.ordjoy.model.repository.review;

import com.ordjoy.model.entity.review.TrackReview;
import com.ordjoy.model.repository.GenericCRUDRepository;

import java.util.List;

public interface TrackReviewRepository extends GenericCRUDRepository<TrackReview, Long> {

    List<TrackReview> findTrackReviewsByUserLogin(String login);

    List<TrackReview> findTrackReviewsByUserId(Long userId);
}