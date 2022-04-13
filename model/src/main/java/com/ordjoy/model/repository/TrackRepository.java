package com.ordjoy.model.repository;

import com.ordjoy.model.entity.review.TrackReview;
import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.entity.track.Track;

import java.util.List;
import java.util.Optional;

public interface TrackRepository extends GenericCRUDRepository<Track, Long> {

    void addTrackToMix(Track track, Mix mix);

    Optional<Track> findByTitle(String title);

    List<TrackReview> findTrackReviewsByTrackTitle(String title, int limit, int offset);

    List<TrackReview> findTrackReviewsByTrackId(Long trackId);

    Long getTrackReviewWithTrackTitlePredicateRecords(String trackTitle);
}