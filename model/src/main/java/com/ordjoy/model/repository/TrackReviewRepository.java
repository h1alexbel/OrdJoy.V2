package com.ordjoy.model.repository;

import com.ordjoy.model.entity.review.TrackReview;

import java.util.List;

public interface TrackReviewRepository extends GenericCRUDRepository<TrackReview, Long> {

    /**
     * Finds TrackReviews by User's login
     *
     * @param login  unique User login
     * @param limit  LIMIT of MixReviews
     * @param offset OFFSET of TrackReviews
     * @return List of TrackReviews
     */
    List<TrackReview> findTrackReviewsByUserLogin(String login, int limit, int offset);

    /**
     * Finds TracksReviews by User's id
     *
     * @param userId User Identifier
     * @return List of TrackReviews
     */
    List<TrackReview> findTrackReviewsByUserId(Long userId);

    /**
     * Finds count of all active TrackReviews from DB which have userLogin like in param
     *
     * @param login unique User login
     * @return Long value of records count
     */
    Long getTrackReviewWithUserLoginPredicateRecords(String login);
}