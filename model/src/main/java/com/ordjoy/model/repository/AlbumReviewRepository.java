package com.ordjoy.model.repository;

import com.ordjoy.model.entity.review.AlbumReview;

import java.util.List;

public interface AlbumReviewRepository extends GenericCRUDRepository<AlbumReview, Long> {

    List<AlbumReview> findAlbumReviewsByUserLogin(String login, int limit, int offset);

    List<AlbumReview> findAlbumReviewsByUserId(Long userId);

    Long getAlbumReviewWithUserLoginPredicateRecords(String login);
}