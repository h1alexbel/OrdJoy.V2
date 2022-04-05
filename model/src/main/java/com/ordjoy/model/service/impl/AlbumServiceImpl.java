package com.ordjoy.model.service.impl;

import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.dto.AlbumReviewDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.entity.review.AlbumReview;
import com.ordjoy.model.entity.track.Album;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.repository.AlbumRepository;
import com.ordjoy.model.repository.AlbumReviewRepository;
import com.ordjoy.model.repository.TrackRepository;
import com.ordjoy.model.service.AlbumService;
import com.ordjoy.model.util.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@Transactional(readOnly = true)
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;
    private final AlbumReviewRepository albumReviewRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository,
                            TrackRepository trackRepository,
                            AlbumReviewRepository albumReviewRepository) {
        this.albumRepository = albumRepository;
        this.trackRepository = trackRepository;
        this.albumReviewRepository = albumReviewRepository;
    }

    @Override
    public List<AlbumDto> list(int limit, int offset) {
        return albumRepository.findAll(limit, offset).stream()
                .map(album -> AlbumDto.builder()
                        .id(album.getId())
                        .title(album.getTitle())
                        .build())
                .toList();
    }

    @Transactional
    @Override
    public AlbumDto save(AlbumDto albumDto) {
        Album savedAlbum = albumRepository.add(Album.builder()
                .title(albumDto.getTitle())
                .albumReviews(new ArrayList<>())
                .tracks(new ArrayList<>())
                .build());
        log.debug(LoggingUtils.ALBUM_WAS_SAVED_IN_SERVICE, savedAlbum);
        return AlbumDto.builder()
                .id(savedAlbum.getId())
                .title(savedAlbum.getTitle())
                .tracks(new ArrayList<>())
                .albumReviews(new ArrayList<>())
                .build();
    }

    @Override
    public Optional<AlbumDto> findAlbumByTitle(String title) {
        if (title != null) {
            return albumRepository.findByTitle(title).stream()
                    .map(album -> AlbumDto.builder()
                            .id(album.getId())
                            .title(album.getTitle())
                            .build())
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public Optional<AlbumDto> findById(Long id) {
        if (id != null) {
            return albumRepository.findById(id).stream()
                    .map(album -> AlbumDto.builder()
                            .id(album.getId())
                            .title(album.getTitle())
                            .build())
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public boolean isAlbumTitleExists(String title) {
        AtomicBoolean result = new AtomicBoolean(false);
        if (title != null) {
            albumRepository.findByTitle(title)
                    .ifPresent(album -> result.set(true));
        }
        boolean value = result.get();
        log.debug(LoggingUtils.IS_ALBUM_ALREADY_EXISTS, title, value);
        return value;
    }

    @Override
    public List<AlbumReviewDto> findAlbumReviewsByAlbumTitle(String albumTitle) {
        if (albumTitle != null) {
            List<AlbumReviewDto> albumReviews = albumRepository
                    .findAlbumReviewsByAlbumTitle(albumTitle).stream()
                    .map(albumReview -> AlbumReviewDto.builder()
                            .id(albumReview.getId())
                            .reviewText(albumReview.getReviewText())
                            .user(UserDto.builder()
                                    .id(albumReview.getUser().getId())
                                    .login(albumReview.getUser().getLogin())
                                    .build())
                            .album(AlbumDto.builder()
                                    .id(albumReview.getAlbum().getId())
                                    .title(albumReview.getAlbum().getTitle())
                                    .build())
                            .build())
                    .toList();
            log.debug(LoggingUtils.REVIEWS_BY_ALBUM_TITLE_SERVICE, albumReviews, albumTitle);
            return albumReviews;
        }
        return Collections.emptyList();
    }

    @Override
    public List<AlbumReviewDto> findAlbumReviewsByAlbumId(Long albumId) {
        if (albumId != null) {
            List<AlbumReviewDto> reviews = albumRepository.findAlbumReviewsByAlbumId(albumId).stream()
                    .map(albumReview -> AlbumReviewDto.builder()
                            .id(albumReview.getId())
                            .reviewText(albumReview.getReviewText())
                            .user(UserDto.builder()
                                    .id(albumReview.getUser().getId())
                                    .login(albumReview.getUser().getLogin())
                                    .build())
                            .album(AlbumDto.builder()
                                    .id(albumReview.getAlbum().getId())
                                    .title(albumReview.getAlbum().getTitle())
                                    .build())
                            .build())
                    .toList();
            log.debug(LoggingUtils.REVIEWS_BY_ALBUM_ID_SERVICE, reviews, albumId);
            return reviews;
        }
        return Collections.emptyList();
    }

    @Override
    public List<TrackDto> findTracksByAlbumTitle(String albumTitle) {
        if (albumTitle != null) {
            List<TrackDto> tracks = albumRepository.findTracksByAlbumTitle(albumTitle).stream()
                    .map(track -> TrackDto.builder()
                            .id(track.getId())
                            .title(track.getTitle())
                            .url(track.getUrl())
                            .album(AlbumDto.builder()
                                    .id(track.getAlbum().getId())
                                    .title(track.getAlbum().getTitle())
                                    .build())
                            .build())
                    .toList();
            log.debug(LoggingUtils.TRACKS_BY_ALBUM_TITLE_SERVICE, tracks, albumTitle);
            return tracks;
        }
        return Collections.emptyList();
    }

    @Override
    public List<TrackDto> findTracksByAlbumId(Long albumId) {
        if (albumId != null) {
            List<TrackDto> tracks = albumRepository.findTracksByAlbumId(albumId).stream()
                    .map(track -> TrackDto.builder()
                            .id(track.getId())
                            .url(track.getUrl())
                            .title(track.getTitle())
                            .album(AlbumDto.builder()
                                    .id(track.getAlbum().getId())
                                    .title(track.getAlbum().getTitle())
                                    .build())
                            .build())
                    .toList();
            log.debug(LoggingUtils.TRACKS_BY_ALBUM_ID_SERVICE, tracks, albumId);
            return tracks;
        }
        return Collections.emptyList();
    }

    @Transactional
    @Override
    public void update(AlbumDto albumDto) {
        if (albumDto != null) {
            albumRepository.update(mapEntityFromDto(albumDto));
            log.debug(LoggingUtils.ALBUM_WAS_UPDATED_IN_SERVICE, albumDto);
        }
    }

    @Transactional
    @Override
    public void deleteAlbum(Long albumId) {
        if (albumId != null) {
            Optional<Album> maybeAlbum = albumRepository.findById(albumId);
            maybeAlbum.ifPresent(album -> {
                List<AlbumReview> albumReviews = albumRepository.findAlbumReviewsByAlbumId(albumId);
                for (AlbumReview albumReview : albumReviews) {
                    albumReviewRepository.delete(albumReview);
                }
                List<Track> tracks = albumRepository.findTracksByAlbumId(albumId);
                for (Track track : tracks) {
                    trackRepository.delete(track);
                }
                albumRepository.delete(album);
                log.debug(LoggingUtils.ALBUM_WAS_DELETED_IN_SERVICE, album);
            });
        }
    }

    private Album mapEntityFromDto(AlbumDto albumDto) {
        Album album = Album.builder()
                .title(albumDto.getTitle())
                .build();
        album.setId(albumDto.getId());
        return album;
    }
}