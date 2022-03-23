package com.ordjoy.model.service.album;

import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.dto.AlbumReviewDto;
import com.ordjoy.model.dto.TrackDto;

import java.util.List;
import java.util.Optional;

public interface AlbumService {

    List<AlbumDto> listAlbums();

    AlbumDto saveAlbum(AlbumDto albumDto);

    Optional<AlbumDto> findAlbumByTitle(String title);

    Optional<AlbumDto> findAlbumById(Long id);

    boolean isAlbumTitleExists(String title);

    List<AlbumReviewDto> findAlbumReviewsByAlbumTitle(String albumTitle);

    List<AlbumReviewDto> findAlbumReviewsByAlbumId(Long albumId);

    List<TrackDto> findTracksByAlbumTitle(String albumTitle);

    List<TrackDto> findTracksByAlbumId(Long albumId);

    void updateAlbum(AlbumDto albumDto);

    void deleteAlbum(Long albumId);
}