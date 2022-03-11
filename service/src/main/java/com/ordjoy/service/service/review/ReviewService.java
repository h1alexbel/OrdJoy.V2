package com.ordjoy.service.service.review;

import com.ordjoy.database.model.review.Review;
import com.ordjoy.service.dto.ReviewDto;

import java.util.List;
import java.util.Optional;

public interface ReviewService<D extends ReviewDto, R extends Review, K> {

    D saveReview(R review);

    Optional<D> findReviewById(Long reviewId);

    List<D> findReviewsByUserLogin(String login);

    List<D> findReviewsByUserId(Long userId);

    List<D> listReviews();

    Optional<D> findReviewById(K key);

    void deleteReview(R review);

    void updateReview(R rev);
}