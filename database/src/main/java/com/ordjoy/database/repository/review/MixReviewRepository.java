package com.ordjoy.database.repository.review;

import com.ordjoy.database.model.review.MixReview;

import java.util.List;

public interface MixReviewRepository {

    List<MixReview> findMixReviewsByUserLogin(String login);

    List<MixReview> findMixReviewsByUserId(Long userId);
}