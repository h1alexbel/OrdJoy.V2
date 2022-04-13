package com.ordjoy.model.repository;

import com.ordjoy.model.entity.review.AlbumReview;
import com.ordjoy.model.entity.track.Album;
import com.ordjoy.model.entity.track.Track;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends GenericCRUDRepository<Album, Long> {

    Optional<Album> findByTitle(String title);

    List<AlbumReview> findAlbumReviewsByAlbumTitle(String title, int limit, int offset);

    List<AlbumReview> findAlbumReviewsByAlbumId(Long albumId);

    List<Track> findTracksByAlbumId(Long albumId);

    List<Track> findTracksByAlbumTitle(String albumTitle, int limit, int offset);

    Long getAlbumReviewWithAlbumTitlePredicateRecords(String albumTitle);

    Long getTrackWithAlbumTitlePredicateRecords(String albumTitle);
}