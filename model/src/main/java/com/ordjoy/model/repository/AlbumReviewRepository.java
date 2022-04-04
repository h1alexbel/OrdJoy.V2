package com.ordjoy.model.repository;

import com.ordjoy.model.entity.review.AlbumReview;
import com.ordjoy.model.repository.GenericCRUDRepository;

import java.util.List;

public interface AlbumReviewRepository extends GenericCRUDRepository<AlbumReview, Long> {

    List<AlbumReview> findAlbumReviewsByUserLogin(String login);

    List<AlbumReview> findAlbumReviewsByUserId(Long userId);
}