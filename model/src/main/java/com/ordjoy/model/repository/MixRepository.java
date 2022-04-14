package com.ordjoy.model.repository;

import com.ordjoy.model.entity.review.MixReview;
import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.entity.track.Track;

import java.util.List;
import java.util.Optional;

public interface MixRepository extends GenericCRUDRepository<Mix, Long> {

    /**
     * Finds Persistent Mix from the DB by its title
     *
     * @param title unique Mix title
     * @return {@link Optional} of Persistent Mix if present or empty {@link Optional}
     */
    Optional<Mix> findByTitle(String title);

    /**
     * Finds all Tracks by mixTitle
     *
     * @param mixTitle unique Mix title
     * @param limit    LIMIT of Tracks
     * @param offset   OFFSET of Tracks
     * @return List of Tracks
     */
    List<Track> findTracksByMixTitle(String mixTitle, int limit, int offset);

    /**
     * Finds all MixReviews by mix title
     *
     * @param mixTitle unique Mix title
     * @param limit    LIMIT of MixReviews
     * @param offset   OFFSET of MixReviews
     * @return List of MixReviews
     */
    List<MixReview> findMixReviewsByMixTitle(String mixTitle, int limit, int offset);

    /**
     * Finds all MixReviews by mixId
     *
     * @param mixId Mix Identifier
     * @return List of MixReviews
     */
    List<MixReview> findMixReviewsByMixId(Long mixId);

    /**
     * Finds count of all active MixReviews from DB which have mixTitle like in param
     *
     * @param mixTitle unique Mix title
     * @return Long value of records count
     */
    Long getMixReviewWithMixTitlePredicateRecords(String mixTitle);

    /**
     * Finds count of all active Tracks from DB which have mixTitle like in param
     *
     * @param mixTitle unique Mix title
     * @return Long value of records count
     */
    Long getTrackWithMixTitlePredicateRecords(String mixTitle);
}