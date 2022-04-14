package com.ordjoy.model.service;

import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.dto.TrackReviewDto;

import java.util.List;
import java.util.Optional;

public interface TrackService extends GenericCRUDService<TrackDto, Long> {

    /**
     * Adds existing TrackDto to existing MixDto
     *
     * @param trackDto TrackDto that exists
     * @param mixDto   MixDto that exists
     */
    void addTrackToMix(TrackDto trackDto, MixDto mixDto);

    /**
     * Finds TrackDto by its title
     *
     * @param title TrackDto title
     * @return Optional value of TrackDto or empty value if not present
     */
    Optional<TrackDto> findTrackByTitle(String title);

    /**
     * Checks is TrackDto title exists, because of TrackDto title must be unique
     *
     * @param title unique TrackDto title
     * @return boolean value {@code true} if exists {@code false} if not
     */
    boolean isTracksTitleExists(String title);

    /**
     * Checks is TrackDto url exists, because of TrackDto url must be unique
     *
     * @param url unique TrackDto url
     * @return boolean value {@code true} if exists {@code false} if not
     */
    boolean isTracksUrlExists(String url);

    /**
     * Finds all TrackReviewDtos by TrackDto title
     *
     * @param trackTitle unique TrackDto title
     * @param limit      LIMIT for pagination
     * @param offset     OFFSET for pagination
     * @return List of TrackReviewDtos
     */
    List<TrackReviewDto> findTrackReviewsByTrackTitle(String trackTitle, int limit, int offset);

    /**
     * Finds all TrackReviewDtos by TrackDto id
     *
     * @param trackId TrackDto Identifier
     * @return List of TrackReviewDtos
     */
    List<TrackReviewDto> findTrackReviewsByTrackId(Long trackId);

    /**
     * Finds count of pages that store TrackReviewDtos from DB which have trackTitle like in param
     *
     * @param trackTitle unique TrackDto title
     * @return Long value of pages count
     */
    Long getTrackReviewWithTrackTitlePredicatePages(String trackTitle);
}