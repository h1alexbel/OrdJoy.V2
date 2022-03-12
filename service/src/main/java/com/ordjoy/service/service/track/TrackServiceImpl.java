package com.ordjoy.service.service.track;

import com.ordjoy.database.model.track.Mix;
import com.ordjoy.database.model.track.Track;
import com.ordjoy.database.repository.track.TrackRepository;
import com.ordjoy.service.dto.AlbumDto;
import com.ordjoy.service.dto.TrackDto;
import com.ordjoy.service.dto.TrackReviewDto;
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
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;

    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public List<TrackDto> listTracks() {
        return trackRepository.findAll().stream()
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

    @Transactional
    @Override
    public TrackDto saveTrack(Track track) {
        Track savedTrack = trackRepository.add(track);
        return TrackDto.builder()
                .id(savedTrack.getId())
                .title(savedTrack.getTitle())
                .url(savedTrack.getUrl())
                .album(AlbumDto.builder()
                        .id(savedTrack.getId())
                        .title(savedTrack.getTitle())
                        .build())
                .build();
    }

    @Transactional
    @Override
    public void addTrackToMix(Track track, Mix mix) {
        if (track != null && mix != null) {
            trackRepository.addTrackToMix(track, mix);
        }
    }

    @Override
    public Optional<TrackDto> findTrackById(Long id) {
        if (id != null) {
            return trackRepository.findById(id).stream()
                    .map(track -> TrackDto.builder()
                            .id(track.getId())
                            .title(track.getTitle())
                            .url(track.getUrl())
                            .album(AlbumDto.builder()
                                    .id(track.getAlbum().getId())
                                    .title(track.getAlbum().getTitle())
                                    .build())
                            .build())
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public Optional<TrackDto> findTrackByTitle(String title) {
        if (title != null) {
            return trackRepository.findByTitle(title).stream()
                    .map(track -> TrackDto.builder()
                            .id(track.getId())
                            .title(track.getTitle())
                            .url(track.getUrl())
                            .album(AlbumDto.builder()
                                    .id(track.getAlbum().getId())
                                    .title(track.getAlbum().getTitle())
                                    .build())
                            .build())
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public boolean isTracksTitleExists(String title) {
        AtomicBoolean result = new AtomicBoolean(false);
        if (title != null) {
            trackRepository.findByTitle(title)
                    .ifPresent(track -> result.set(true));
        }
        return result.get();
    }

    @Override
    public List<TrackReviewDto> findTrackReviewsByTrackTitle(String trackTitle) {
        if (trackTitle != null) {
            return trackRepository.findTrackReviewsByTrackTitle(trackTitle).stream()
                    .map(trackReview -> TrackReviewDto.builder()
                            .id(trackReview.getId())
                            .reviewText(trackReview.getReviewText())
                            .user(UserDto.builder()
                                    .id(trackReview.getUser().getId())
                                    .login(trackReview.getUser().getLogin())
                                    .build())
                            .track(TrackDto.builder()
                                    .id(trackReview.getTrack().getId())
                                    .title(trackReview.getTrack().getTitle())
                                    .url(trackReview.getTrack().getUrl())
                                    .build())
                            .build())
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<TrackReviewDto> findTrackReviewsByTrackId(Long trackId) {
        if (trackId != null) {
            return trackRepository.findTrackReviewsByTrackId(trackId).stream()
                    .map(trackReview -> TrackReviewDto.builder()
                            .id(trackReview.getId())
                            .reviewText(trackReview.getReviewText())
                            .user(UserDto.builder()
                                    .id(trackReview.getUser().getId())
                                    .login(trackReview.getUser().getLogin())
                                    .build())
                            .track(TrackDto.builder()
                                    .id(trackReview.getTrack().getId())
                                    .title(trackReview.getTrack().getTitle())
                                    .url(trackReview.getTrack().getUrl())
                                    .build())
                            .build())
                    .toList();
        }
        return Collections.emptyList();
    }

    @Transactional
    @Override
    public void updateTrack(Track track) {
        if (track != null) {
            trackRepository.update(track);
        }
    }

    @Transactional
    @Override
    public void deleteTrack(Track track) {
        if (track != null) {
            trackRepository.delete(track);
        }
    }
}