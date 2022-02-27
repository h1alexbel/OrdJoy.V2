package com.ordjoy.database.repository.review;

import com.ordjoy.database.model.review.MixReview;
import com.ordjoy.database.repository.GenericCRUDRepository;

import java.util.List;

public interface MixReviewRepository extends GenericCRUDRepository<MixReview, Long> {

    List<MixReview> findMixReviewsByUserLogin(String login);

    List<MixReview> findMixReviewsByUserId(Long userId);
}