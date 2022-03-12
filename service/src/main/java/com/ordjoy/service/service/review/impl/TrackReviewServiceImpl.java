package com.ordjoy.service.service.review.impl;

import com.ordjoy.database.model.review.TrackReview;
import com.ordjoy.database.repository.review.TrackReviewRepository;
import com.ordjoy.service.dto.AlbumDto;
import com.ordjoy.service.dto.TrackDto;
import com.ordjoy.service.dto.TrackReviewDto;
import com.ordjoy.service.dto.UserDto;
import com.ordjoy.service.service.review.TrackReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public TrackReviewDto saveReview(TrackReview trackReview) {
        TrackReview savedReview = trackReviewRepository.add(trackReview);
        return TrackReviewDto.builder()
                .id(savedReview.getId())
                .reviewText(savedReview.getReviewText())
                .user(UserDto.builder()
                        .id(savedReview.getUser().getId())
                        .login(savedReview.getUser().getLogin())
                        .build())
                .track(TrackDto.builder()
                        .id(savedReview.getTrack().getId())
                        .title(savedReview.getTrack().getTitle())
                        .url(savedReview.getTrack().getUrl())
                        .album(AlbumDto.builder()
                                .id(savedReview.getTrack().getAlbum().getId())
                                .title(savedReview.getTrack().getAlbum().getTitle())
                                .build())
                        .build())
                .build();
    }

    @Override
    public Optional<TrackReviewDto> findReviewById(Long reviewId) {
        if (reviewId != null) {
            return trackReviewRepository.findById(reviewId).stream()
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
                                    .album(AlbumDto.builder()
                                            .id(trackReview.getTrack().getAlbum().getId())
                                            .title(trackReview.getTrack().getAlbum().getTitle())
                                            .build())
                                    .build())
                            .build())
                    .findFirst();
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void deleteReview(TrackReview trackReview) {
        if (trackReview != null) {
            trackReviewRepository.delete(trackReview);
        }
    }

    @Transactional
    @Override
    public void updateReview(TrackReview trackReview) {
        if (trackReview != null) {
            trackReviewRepository.update(trackReview);
        }
    }

    @Override
    public List<TrackReviewDto> findReviewsByUserLogin(String login) {
        if (login != null) {
            return trackReviewRepository.findTrackReviewsByUserLogin(login).stream()
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
                                    .album(AlbumDto.builder()
                                            .id(trackReview.getTrack().getAlbum().getId())
                                            .title(trackReview.getTrack().getAlbum().getTitle())
                                            .build())
                                    .build())
                            .build())
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<TrackReviewDto> findReviewsByUserId(Long userId) {
        if (userId != null) {
            return trackReviewRepository.findTrackReviewsByUserId(userId).stream()
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
                                    .album(AlbumDto.builder()
                                            .id(trackReview.getTrack().getAlbum().getId())
                                            .title(trackReview.getTrack().getAlbum().getTitle())
                                            .build())
                                    .build())
                            .build())
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<TrackReviewDto> listReviews() {
        return trackReviewRepository.findAll().stream()
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
                                .album(AlbumDto.builder()
                                        .id(trackReview.getTrack().getAlbum().getId())
                                        .title(trackReview.getTrack().getAlbum().getTitle())
                                        .build())
                                .build())
                        .build())
                .toList();
    }
}