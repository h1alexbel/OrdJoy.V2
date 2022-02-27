package com.ordjoy.database.repository.review;

import com.ordjoy.database.model.review.AlbumReview;

import java.util.List;

public interface AlbumReviewRepository {

    List<AlbumReview> findAlbumReviewsByUserLogin(String login);

    List<AlbumReview> findAlbumReviewsByUserId(Long userId);
}