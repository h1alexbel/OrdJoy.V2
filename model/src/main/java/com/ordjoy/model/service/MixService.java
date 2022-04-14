package com.ordjoy.model.service;

import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.MixReviewDto;
import com.ordjoy.model.dto.TrackDto;

import java.util.List;
import java.util.Optional;

public interface MixService extends GenericCRUDService<MixDto, Long> {

    /**
     * Finds AlbumDto by albumTitle
     *
     * @param title AlbumDto title
     * @return Optional value of MixDto or empty value if not present
     */
    Optional<MixDto> findMixByTitle(String title);

    /**
     * Checks is MixDto title exists, because of MixDto title must be unique
     *
     * @param title unique MixDto title
     * @return boolean value {@code true} if exists {@code false} if not
     */
    boolean isMixTitleExists(String title);

    /**
     * Finds all TrackDtos by MixDto title
     *
     * @param mixTitle unique MixDto title
     * @param limit    LIMIT for pagination
     * @param offset   OFFSET for pagination
     * @return List of TrackDtos
     */
    List<TrackDto> findTracksByMixTitle(String mixTitle, int limit, int offset);

    /**
     * Finds all MixReviewDtos by MixDto title
     *
     * @param mixTitle unique AlbumDto title
     * @param limit    LIMIT for pagination
     * @param offset   OFFSET for pagination
     * @return List of MixReviewDtos
     */
    List<MixReviewDto> findMixReviewsByMixTitle(String mixTitle, int limit, int offset);

    /**
     * Finds all MixReviewDtos by MixDto id
     *
     * @param mixId MixDto Identifier
     * @return List of MixReviewDtos
     */
    List<MixReviewDto> findMixReviewsByMixId(Long mixId);

    /**
     * Finds count of pages that store MixReviewDtos from DB which have mixTitle like in param
     *
     * @param mixTitle unique MixDto title
     * @return Long value of pages count
     */
    Long getMixReviewWithMixTitlePredicatePages(String mixTitle);

    /**
     * Finds count of pages that store TrackDtos from DB which have mixTitle like in param
     *
     * @param mixTitle unique MixDto title
     * @return Long value of pages count
     */
    Long getTrackWithMixTitlePredicatePages(String mixTitle);
}