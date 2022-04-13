package com.ordjoy.model.service;

import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.dto.AlbumReviewDto;
import com.ordjoy.model.dto.TrackDto;

import java.util.List;
import java.util.Optional;

public interface AlbumService extends GenericCRUDService<AlbumDto, Long> {

    Optional<AlbumDto> findAlbumByTitle(String title);

    boolean isAlbumTitleExists(String title);

    List<AlbumReviewDto> findAlbumReviewsByAlbumTitle(String albumTitle, int limit, int offset);

    List<AlbumReviewDto> findAlbumReviewsByAlbumId(Long albumId);

    List<TrackDto> findTracksByAlbumTitle(String albumTitle, int limit, int offset);

    List<TrackDto> findTracksByAlbumId(Long albumId);

    void deleteAlbum(Long albumId);

    Long getAlbumReviewWithAlbumTitlePredicatePages(String albumTitle);

    Long getTrackWithAlbumTitlePredicatePages(String albumTitle);
}