package com.ordjoy.service.service.album;

import com.ordjoy.database.model.track.Album;
import com.ordjoy.database.repository.album.AlbumRepository;
import com.ordjoy.service.dto.AlbumDto;
import com.ordjoy.service.dto.AlbumReviewDto;
import com.ordjoy.service.dto.TrackDto;
import com.ordjoy.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional(readOnly = true)
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public List<AlbumDto> listAlbums() {
        return albumRepository.findAll().stream()
                .map(album -> AlbumDto.builder()
                        .id(album.getId())
                        .title(album.getTitle())
                        .build())
                .toList();
    }

    @Transactional
    @Override
    public AlbumDto saveAlbum(Album album) {
        Album savedAlbum = albumRepository.add(album);
        return AlbumDto.builder()
                .id(savedAlbum.getId())
                .title(savedAlbum.getTitle())
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
    public Optional<AlbumDto> findAlbumById(Long id) {
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
        return result.get();
    }

    @Override
    public List<AlbumReviewDto> findAlbumReviewsByAlbumTitle(String albumTitle) {
        if (albumTitle != null) {
            return albumRepository.findAlbumReviewsByAlbumTitle(albumTitle).stream()
                    .map(albumReview -> AlbumReviewDto.builder()
                            .id(albumReview.getId())
                            .reviewText(albumReview.getReviewText())
                            .user(UserDto.builder()
                                    .login(albumReview.getUser().getLogin())
                                    .build())
                            .album(AlbumDto.builder()
                                    .id(albumReview.getAlbum().getId())
                                    .title(albumReview.getAlbum().getTitle())
                                    .build())
                            .build())
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<AlbumReviewDto> findAlbumReviewsByAlbumId(Long albumId) {
        if (albumId != null) {
            return albumRepository.findAlbumReviewsByAlbumId(albumId).stream()
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
        }
        return Collections.emptyList();
    }

    @Override
    public List<TrackDto> findTracksByAlbumTitle(String albumTitle) {
        if (albumTitle != null) {
            return albumRepository.findTracksByAlbumTitle(albumTitle).stream()
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
        }
        return Collections.emptyList();
    }

    @Override
    public List<TrackDto> findTracksByAlbumId(Long albumId) {
        if (albumId != null) {
            return albumRepository.findTracksByAlbumId(albumId).stream()
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
        }
        return Collections.emptyList();
    }

    @Transactional
    @Override
    public void updateAlbum(Album album) {
        if (album != null) {
            albumRepository.update(album);
        }
    }

    @Transactional
    @Override
    public void deleteAlbum(Album album) {
        if (album != null) {
            albumRepository.delete(album);
        }
    }
}