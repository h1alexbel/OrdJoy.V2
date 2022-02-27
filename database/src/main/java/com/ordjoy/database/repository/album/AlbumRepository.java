package com.ordjoy.database.repository.album;

import com.ordjoy.database.model.review.AlbumReview;
import com.ordjoy.database.model.track.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository {

    Optional<Album> findAlbumByTitle(String title);

    List<AlbumReview> findAlbumReviewsByAlbumTitle(String title);

    List<AlbumReview> findAlbumReviewsByAlbumId(Long albumId);
}