package com.ordjoy.database.repository.album;

import com.ordjoy.database.model.review.AlbumReview;
import com.ordjoy.database.model.track.Album;
import com.ordjoy.database.repository.GenericCRUDRepository;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends GenericCRUDRepository<Album, Long> {

    Optional<Album> findAlbumByTitle(String title);

    List<AlbumReview> findAlbumReviewsByAlbumTitle(String title);

    List<AlbumReview> findAlbumReviewsByAlbumId(Long albumId);
}