package com.ordjoy.model.repository;

import com.ordjoy.model.entity.review.TrackReview;
import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.entity.track.Track;

import java.util.List;
import java.util.Optional;

public interface TrackRepository extends GenericCRUDRepository<Track, Long> {

    /**
     * Adds existing Track to existing Mix
     *
     * @param track Persistent Track that exists
     * @param mix   Persistent Mix that exists
     */
    void addTrackToMix(Track track, Mix mix);

    /**
     * Finds Persistent Track from the DB by its title
     *
     * @param title unique Track title
     * @return {@link Optional} of Persistent Track if present or empty {@link Optional}
     */
    Optional<Track> findByTitle(String title);

    /**
     * Finds Persistent Track from the DB by its title
     *
     * @param url unique Track url
     * @return {@link Optional} of Persistent Track if present or empty {@link Optional}
     */
    Optional<Track> findByUrl(String url);

    /**
     * Finds all TrackReviews by track title
     *
     * @param title  unique track title
     * @param limit  LIMIT of TrackReviews
     * @param offset OFFSET of TrackReviews
     * @return List of TrackReviews
     */
    List<TrackReview> findTrackReviewsByTrackTitle(String title, int limit, int offset);

    /**
     * Finds all TrackReviews by trackId
     *
     * @param trackId Track Identifier
     * @return List of TrackReviews
     */
    List<TrackReview> findTrackReviewsByTrackId(Long trackId);

    /**
     * Finds count of all active TrackReviews from DB which have trackTitle like in param
     *
     * @param trackTitle unique Track title
     * @return Long value of records count
     */
    Long getTrackReviewWithTrackTitlePredicateRecords(String trackTitle);
}