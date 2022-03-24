package com.ordjoy.model.service.track;

import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.dto.TrackReviewDto;
import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.entity.track.Album;
import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.util.LoggingUtils;
import com.ordjoy.model.repository.mix.MixRepository;
import com.ordjoy.model.repository.track.TrackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@Transactional(readOnly = true)
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;
    private final MixRepository mixRepository;

    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository, MixRepository mixRepository) {
        this.trackRepository = trackRepository;
        this.mixRepository = mixRepository;
    }

    @Override
    public List<TrackDto> listTracks(int limit, int offset) {
        return trackRepository.findAll(limit, offset).stream()
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
    public TrackDto saveTrack(TrackDto trackDto) {
        Track trackToSave = Track.builder()
                .title(trackDto.getTitle())
                .url(trackDto.getUrl())
                .album(Album.builder()
                        .title(trackDto.getTitle())
                        .build())
                .build();
        trackToSave.getAlbum().setId(trackDto.getAlbum().getId());
        Track savedTrack = trackRepository.add(trackToSave);
        log.debug(LoggingUtils.TRACK_WAS_SAVED_SERVICE, savedTrack);
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
    public void addTrackToMix(TrackDto trackDto, MixDto mixDto) {
        Optional<Track> maybeTrack = trackRepository.findById(trackDto.getId());
        Optional<Mix> maybeMix = mixRepository.findById(mixDto.getId());
        if (maybeTrack.isPresent() && maybeMix.isPresent()) {
            Track track = maybeTrack.get();
            Mix mix = maybeMix.get();
            trackRepository.addTrackToMix(track, mix);
            log.debug(LoggingUtils.TRACK_WAS_ADDED_TO_MIX_SERVICE, track, mix);
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
        AtomicBoolean isTracksTitleExistsResult = new AtomicBoolean(false);
        if (title != null) {
            trackRepository.findByTitle(title)
                    .ifPresent(track -> {
                        isTracksTitleExistsResult.set(true);
                        log.debug(LoggingUtils.IS_TRACK_EXISTS,
                                track, title, isTracksTitleExistsResult);
                    });
        }
        return isTracksTitleExistsResult.get();
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
    public void updateTrack(TrackDto trackDto) {
        if (trackDto != null) {
            Optional<Track> maybeTrack = trackRepository.findById(trackDto.getId());
            maybeTrack.ifPresent(track -> {
                trackRepository.update(track);
                log.debug(LoggingUtils.TRACK_WAS_UPDATED_SERVICE, trackDto);
            });
        }
    }

    @Transactional
    @Override
    public void deleteTrack(Long trackId) {
        if (trackId != null) {
            Optional<Track> maybeTrack = trackRepository.findById(trackId);
            maybeTrack.ifPresent(track -> {
                trackRepository.delete(track);
                log.debug(LoggingUtils.TRACK_WAS_DELETE_SERVICE, track);
            });
        }
    }
}