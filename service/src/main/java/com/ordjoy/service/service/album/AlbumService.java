package com.ordjoy.service.service.album;

import com.ordjoy.database.model.track.Album;
import com.ordjoy.service.dto.AlbumDto;
import com.ordjoy.service.dto.AlbumReviewDto;
import com.ordjoy.service.dto.TrackDto;

import java.util.List;
import java.util.Optional;

public interface AlbumService {

    List<AlbumDto> listAlbums();

    AlbumDto saveAlbum(Album album);

    Optional<AlbumDto> findAlbumByTitle(String title);

    Optional<AlbumDto> findAlbumById(Long id);

    boolean isAlbumTitleExists(String title);

    List<AlbumReviewDto> findAlbumReviewsByAlbumTitle(String albumTitle);

    List<AlbumReviewDto> findAlbumReviewsByAlbumId(Long albumId);

    List<TrackDto> findTracksByAlbumTitle(String albumTitle);

    List<TrackDto> findTracksByAlbumId(Long albumId);

    void updateAlbum(Album album);

    void deleteAlbumById(Long id);
}