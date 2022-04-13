package com.ordjoy.model.repository;

import com.ordjoy.model.entity.review.MixReview;

import java.util.List;

public interface MixReviewRepository extends GenericCRUDRepository<MixReview, Long> {

    List<MixReview> findMixReviewsByUserLogin(String login, int limit, int offset);

    List<MixReview> findMixReviewsByUserId(Long userId);

    Long getMixReviewWithUserLoginPredicateRecords(String login);
}