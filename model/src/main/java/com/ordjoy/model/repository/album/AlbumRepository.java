package com.ordjoy.model.repository.album;

import com.ordjoy.model.entity.review.AlbumReview;
import com.ordjoy.model.entity.track.Album;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.repository.GenericCRUDRepository;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends GenericCRUDRepository<Album, Long> {

    Optional<Album> findByTitle(String title);

    List<AlbumReview> findAlbumReviewsByAlbumTitle(String title);

    List<AlbumReview> findAlbumReviewsByAlbumId(Long albumId);

    List<Track> findTracksByAlbumId(Long albumId);

    List<Track> findTracksByAlbumTitle(String albumTitle);
}