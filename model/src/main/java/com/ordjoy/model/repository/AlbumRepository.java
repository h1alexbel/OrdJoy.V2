package com.ordjoy.model.repository;

import com.ordjoy.model.entity.review.AlbumReview;
import com.ordjoy.model.entity.track.Album;
import com.ordjoy.model.entity.track.Track;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends GenericCRUDRepository<Album, Long> {

    /**
     * Finds Persistent Album from the DB by its title
     *
     * @param title unique Album title
     * @return {@link Optional} of Persistent Object if present or empty {@link Optional}
     */
    Optional<Album> findByTitle(String title);

    /**
     * Finds all AlbumReviews by album title
     *
     * @param title  unique Album title
     * @param limit  LIMIT of AlbumReviews
     * @param offset OFFSET of AlbumReviews
     * @return List of AlbumReviews
     */
    List<AlbumReview> findAlbumReviewsByAlbumTitle(String title, int limit, int offset);

    /**
     * Finds all AlbumReviews by albumId
     *
     * @param albumId Album Identifier
     * @return List of AlbumReviews
     */
    List<AlbumReview> findAlbumReviewsByAlbumId(Long albumId);

    /**
     * Finds all Tracks by albumId
     *
     * @param albumId Album Identifier
     * @return List of Tracks
     */
    List<Track> findTracksByAlbumId(Long albumId);

    /**
     * Finds all Tracks by albumTitle
     *
     * @param albumTitle unique Album title
     * @param limit      LIMIT of Tracks
     * @param offset     OFFSET of Tracks
     * @return List of Tracks
     */
    List<Track> findTracksByAlbumTitle(String albumTitle, int limit, int offset);

    /**
     * Finds count of all active AlbumReviews from DB which have albumTitle like in param
     *
     * @param albumTitle unique Album title
     * @return Long value of records count
     */
    Long getAlbumReviewWithAlbumTitlePredicateRecords(String albumTitle);

    /**
     * Finds count of all active Tracks from DB which have albumTitle like in param
     *
     * @param albumTitle unique Album title
     * @return Long value of records count
     */
    Long getTrackWithAlbumTitlePredicateRecords(String albumTitle);
}