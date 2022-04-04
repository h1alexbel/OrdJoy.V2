package com.ordjoy.model.repository;

import com.ordjoy.model.entity.review.MixReview;
import com.ordjoy.model.repository.GenericCRUDRepository;

import java.util.List;

public interface MixReviewRepository extends GenericCRUDRepository<MixReview, Long> {

    List<MixReview> findMixReviewsByUserLogin(String login);

    List<MixReview> findMixReviewsByUserId(Long userId);
}