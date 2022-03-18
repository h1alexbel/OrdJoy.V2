package com.ordjoy.model.service.album;

import com.ordjoy.model.entity.review.AlbumReview;
import com.ordjoy.model.entity.track.Album;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.repository.album.AlbumRepository;
import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.dto.AlbumReviewDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public AlbumDto saveAlbum(AlbumDto albumDto) {
        Album savedAlbum = albumRepository.add(Album.builder()
                .title(albumDto.getTitle())
                .build());
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
    public void updateAlbum(AlbumDto albumDto) {
        if (albumDto != null) {
            Album album = Album.builder()
                    .title(albumDto.getTitle())
                    .tracks(tracksFromTracksDto(albumDto.getTracks()))
                    .albumReviews(albumReviewsFromDto(albumDto.getAlbumReviews()))
                    .build();
            albumRepository.update(album);
        }
    }

    @Transactional
    @Override
    public void deleteAlbum(AlbumDto albumDto) {
        if (albumDto != null) {
            Album album = Album.builder()
                    .title(albumDto.getTitle())
                    .tracks(tracksFromTracksDto(albumDto.getTracks()))
                    .albumReviews(albumReviewsFromDto(albumDto.getAlbumReviews()))
                    .build();
            albumRepository.delete(album);
        }
    }

    private List<Track> tracksFromTracksDto(List<TrackDto> tracksDto) {
        if (tracksDto != null) {
            List<Track> tracks = new ArrayList<>();
            for (TrackDto trackDto : tracksDto) {
                tracks.add(Track.builder()
                        .title(trackDto.getTitle())
                        .url(trackDto.getUrl())
                        .album(Album.builder()
                                .title(trackDto.getAlbum().getTitle())
                                .build())
                        .build());
            }
            return tracks;
        }
        return Collections.emptyList();
    }

    private List<AlbumReview> albumReviewsFromDto(List<AlbumReviewDto> albumReviewsDto) {
        if (albumReviewsDto != null) {
            List<AlbumReview> albumReviews = new ArrayList<>();
            for (AlbumReviewDto albumReviewDto : albumReviewsDto) {
                albumReviews.add(AlbumReview.builder()
                        .reviewText(albumReviewDto.getReviewText())
                        .user(User.builder()
                                .login(albumReviewDto.getUser().getLogin())
                                .build())
                        .album(Album.builder()
                                .title(albumReviewDto.getAlbum().getTitle())
                                .build())
                        .build());
            }
            return albumReviews;
        }
        return Collections.emptyList();
    }
}