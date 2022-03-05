package com.ordjoy.database.repository.review;

import com.ordjoy.database.model.review.AlbumReview;
import com.ordjoy.database.repository.GenericCRUDRepository;

import java.util.List;

public interface AlbumReviewRepository extends GenericCRUDRepository<AlbumReview, Long> {

    List<AlbumReview> findAlbumReviewsByUserLogin(String login);

    List<AlbumReview> findAlbumReviewsByUserId(Long userId);
}