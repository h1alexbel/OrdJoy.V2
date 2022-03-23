package com.ordjoy.model.service.review;

import com.ordjoy.model.dto.ReviewDto;

import java.util.List;
import java.util.Optional;

public interface ReviewService<D extends ReviewDto, K> {

    D saveReview(D reviewDto);

    Optional<D> findReviewById(K reviewId);

    List<D> findReviewsByUserLogin(String login);

    List<D> findReviewsByUserId(Long userId);

    List<D> listReviews();

    void deleteReview(D reviewDto);

    void updateReview(D reviewDto);
}