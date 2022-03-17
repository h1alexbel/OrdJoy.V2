package com.ordjoy.model.service.review;

import com.ordjoy.model.dto.ReviewDto;

import java.util.List;
import java.util.Optional;

public interface ReviewService<D extends ReviewDto, K> {

    D saveReview(D reviewDto);

    Optional<D> findReviewById(Long reviewId);

    List<D> findReviewsByUserLogin(String login);

    List<D> findReviewsByUserId(Long userId);

    List<D> listReviews();

    Optional<D> findReviewById(K key);

    void deleteReview(D reviewDto);

    void updateReview(D reviewDto);
}