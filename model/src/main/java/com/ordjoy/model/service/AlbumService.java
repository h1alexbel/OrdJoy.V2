package com.ordjoy.model.service;

import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.dto.AlbumReviewDto;
import com.ordjoy.model.dto.TrackDto;

import java.util.List;
import java.util.Optional;

public interface AlbumService extends GenericCRUDService<AlbumDto, Long> {

    /**
     * Finds AlbumDto by albumTitle
     *
     * @param title AlbumDto title
     * @return Optional value of AlbumDto or empty value if not present
     */
    Optional<AlbumDto> findAlbumByTitle(String title);

    /**
     * Checks is AlbumDto title exists, because of AlbumDto title must be unique
     *
     * @param title unique AlbumDto title
     * @return boolean value {@code true} if exists {@code false} if not
     */
    boolean isAlbumTitleExists(String title);

    /**
     * Finds all AlbumReviewDtos by AlbumDto title
     *
     * @param albumTitle unique AlbumDto title
     * @param limit      LIMIT for pagination
     * @param offset     OFFSET for pagination
     * @return List of AlbumReviewDtos
     */
    List<AlbumReviewDto> findAlbumReviewsByAlbumTitle(String albumTitle, int limit, int offset);

    /**
     * Finds all AlbumReviewDtos by AlbumDto id
     *
     * @param albumId AlbumDto Identifier
     * @return List of AlbumReviewDtos
     */
    List<AlbumReviewDto> findAlbumReviewsByAlbumId(Long albumId);

    /**
     * Finds all TrackDtos by AlbumDto title
     *
     * @param albumTitle unique AlbumDto title
     * @param limit      LIMIT for pagination
     * @param offset     OFFSET for pagination
     * @return List of TrackDtos
     */
    List<TrackDto> findTracksByAlbumTitle(String albumTitle, int limit, int offset);

    /**
     * Finds all TrackDtos by AlbumDto id
     *
     * @param albumId AlbumDto Identifier
     * @return List of TrackDtos
     */
    List<TrackDto> findTracksByAlbumId(Long albumId);

    /**
     * Finds count of pages that store AlbumReviewDtos from DB which have albumTitle like in param
     *
     * @param albumTitle unique AlbumDto title
     * @return Long value of pages count
     */
    Long getAlbumReviewWithAlbumTitlePredicatePages(String albumTitle);

    /**
     * Finds count of pages that store TrackDtos from DB which have albumTitle like in param
     *
     * @param albumTitle unique AlbumDto title
     * @return Long value of pages count
     */
    Long getTrackWithAlbumTitlePredicatePages(String albumTitle);
}