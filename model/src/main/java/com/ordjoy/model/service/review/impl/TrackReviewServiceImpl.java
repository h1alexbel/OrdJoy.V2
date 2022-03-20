package com.ordjoy.model.service.review.impl;

import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.dto.TrackReviewDto;
import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.entity.review.TrackReview;
import com.ordjoy.model.entity.track.Album;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.log.LoggingUtils;
import com.ordjoy.model.repository.review.TrackReviewRepository;
import com.ordjoy.model.service.review.TrackReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class TrackReviewServiceImpl implements TrackReviewService {

    private final TrackReviewRepository trackReviewRepository;

    @Autowired
    public TrackReviewServiceImpl(TrackReviewRepository trackReviewRepository) {
        this.trackReviewRepository = trackReviewRepository;
    }

    @Transactional
    @Override
    public TrackReviewDto saveReview(TrackReviewDto trackReviewDto) {
        TrackReview trackReviewToSave = TrackReview.builder()
                .reviewText(trackReviewDto.getReviewText())
                .track(Track.builder()
                        .title(trackReviewDto.getTrack().getTitle())
                        .url(trackReviewDto.getTrack().getUrl())
                        .album(Album.builder()
                                .title(trackReviewDto.getTrack().getAlbum().getTitle())
                                .build())
                        .build())
                .user(User.builder()
                        .login(trackReviewDto.getUser().getLogin())
                        .email(trackReviewDto.getUser().getEmail())
                        .build())
                .build();
        trackReviewToSave.getUser().setId(trackReviewDto.getUser().getId());
        trackReviewDto.getTrack().getAlbum().setId(trackReviewDto.getTrack().getAlbum().getId());
        trackReviewToSave.getTrack().setId(trackReviewDto.getTrack().getId());
        TrackReview savedTrackReview = trackReviewRepository.add(trackReviewToSave);
        log.debug(LoggingUtils.TRACK_REVIEW_WAS_ADDED_SERVICE, savedTrackReview);
        return buildTrackReviewDtoFromEntity(savedTrackReview);
    }

    @Override
    public Optional<TrackReviewDto> findReviewById(Long reviewId) {
        if (reviewId != null) {
            return trackReviewRepository.findById(reviewId).stream()
                    .map(this::buildTrackReviewDtoFromEntity)
                    .findFirst();
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void deleteReview(TrackReviewDto trackReviewDto) {
        if (trackReviewDto != null) {
            Optional<TrackReview> maybeTrackReview = trackReviewRepository
                    .findById(trackReviewDto.getId());
            maybeTrackReview.ifPresent(trackReview -> {
                trackReviewRepository.delete(trackReview);
                log.debug(LoggingUtils.TRACK_REVIEW_WAS_DELETED_SERVICE, trackReview);
            });
        }
    }

    @Transactional
    @Override
    public void updateReview(TrackReviewDto trackReviewDto) {
        if (trackReviewDto != null) {
            Optional<TrackReview> maybeTrackReview = trackReviewRepository
                    .findById(trackReviewDto.getId());
            maybeTrackReview.ifPresent(trackReview -> {
                trackReviewRepository.update(trackReview);
                log.debug(LoggingUtils.TRACK_REVIEW_WAS_UPDATED_SERVICE, trackReview);
            });
        }
    }

    @Override
    public List<TrackReviewDto> findReviewsByUserLogin(String login) {
        if (login != null) {
            return trackReviewRepository.findTrackReviewsByUserLogin(login).stream()
                    .map(this::buildTrackReviewDtoFromEntity)
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<TrackReviewDto> findReviewsByUserId(Long userId) {
        if (userId != null) {
            return trackReviewRepository.findTrackReviewsByUserId(userId).stream()
                    .map(this::buildTrackReviewDtoFromEntity)
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<TrackReviewDto> listReviews() {
        return trackReviewRepository.findAll().stream()
                .map(this::buildTrackReviewDtoFromEntity)
                .toList();
    }

    private TrackReviewDto buildTrackReviewDtoFromEntity(TrackReview trackReview) {
        return TrackReviewDto.builder()
                .id(trackReview.getId())
                .reviewText(trackReview.getReviewText())
                .user(UserDto.builder()
                        .id(trackReview.getUser().getId())
                        .login(trackReview.getUser().getLogin())
                        .email(trackReview.getUser().getEmail())
                        .build())
                .track(TrackDto.builder()
                        .id(trackReview.getTrack().getId())
                        .title(trackReview.getTrack().getTitle())
                        .url(trackReview.getTrack().getUrl())
                        .album(AlbumDto.builder()
                                .id(trackReview.getTrack().getAlbum().getId())
                                .title(trackReview.getTrack().getAlbum().getTitle())
                                .build())
                        .build())
                .build();
    }
}