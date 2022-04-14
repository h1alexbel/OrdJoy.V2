package com.ordjoy.model.repository;

import com.ordjoy.model.entity.review.AlbumReview;

import java.util.List;

public interface AlbumReviewRepository extends GenericCRUDRepository<AlbumReview, Long> {

    /**
     * Finds AlbumReviews by User's login
     *
     * @param login  unique User login
     * @param limit  LIMIT of AlbumReviews
     * @param offset OFFSET of AlbumReviews
     * @return List of AlbumReviews
     */
    List<AlbumReview> findAlbumReviewsByUserLogin(String login, int limit, int offset);

    /**
     * Finds AlbumReviews by User's id
     *
     * @param userId User Identifier
     * @return List of AlbumReviews
     */
    List<AlbumReview> findAlbumReviewsByUserId(Long userId);

    /**
     * Finds count of all active AlbumReviews from DB which have userLogin like in param
     *
     * @param login unique User login
     * @return Long value of records count
     */
    Long getAlbumReviewWithUserLoginPredicateRecords(String login);
}