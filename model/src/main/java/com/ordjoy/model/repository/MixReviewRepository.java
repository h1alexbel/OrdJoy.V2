package com.ordjoy.model.repository;

import com.ordjoy.model.entity.review.MixReview;

import java.util.List;

public interface MixReviewRepository extends GenericCRUDRepository<MixReview, Long> {

    /**
     * Finds MixReviews by User's login
     *
     * @param login  unique User login
     * @param limit  LIMIT of MixReviews
     * @param offset OFFSET of MixReviews
     * @return List of MixReviews
     */
    List<MixReview> findMixReviewsByUserLogin(String login, int limit, int offset);

    /**
     * Finds MixReviews by User's id
     *
     * @param userId User Identifier
     * @return List of MixReviews
     */
    List<MixReview> findMixReviewsByUserId(Long userId);

    /**
     * Finds count of all active MixReviews from DB which have userLogin like in param
     *
     * @param login unique User login
     * @return Long value of records count
     */
    Long getMixReviewWithUserLoginPredicateRecords(String login);
}