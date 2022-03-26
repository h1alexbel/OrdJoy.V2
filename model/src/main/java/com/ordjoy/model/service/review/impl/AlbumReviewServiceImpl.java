package com.ordjoy.model.service.review.impl;

import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.dto.AlbumReviewDto;
import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.entity.review.AlbumReview;
import com.ordjoy.model.entity.track.Album;
import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.repository.review.AlbumReviewRepository;
import com.ordjoy.model.service.review.AlbumReviewService;
import com.ordjoy.model.util.LoggingUtils;
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
public class AlbumReviewServiceImpl implements AlbumReviewService {

    private final AlbumReviewRepository albumReviewRepository;

    @Autowired
    public AlbumReviewServiceImpl(AlbumReviewRepository albumReviewRepository) {
        this.albumReviewRepository = albumReviewRepository;
    }

    @Transactional
    @Override
    public AlbumReviewDto saveReview(AlbumReviewDto albumReviewDto) {
        AlbumReview albumReviewToSave = AlbumReview.builder()
                .reviewText(albumReviewDto.getReviewText())
                .album(Album.builder()
                        .title(albumReviewDto.getAlbum().getTitle())
                        .build())
                .user(User.builder()
                        .login(albumReviewDto.getUser().getLogin())
                        .email(albumReviewDto.getUser().getEmail())
                        .build())
                .build();
        albumReviewToSave.getUser().setId(albumReviewDto.getUser().getId());
        albumReviewToSave.getAlbum().setId(albumReviewDto.getAlbum().getId());
        AlbumReview savedAlbumReview = albumReviewRepository.add(albumReviewToSave);
        log.debug(LoggingUtils.ALBUM_REVIEW_WAS_ADDED_SERVICE, savedAlbumReview);
        return buildAlbumReviewDtoFromEntity(savedAlbumReview);
    }

    @Override
    public Optional<AlbumReviewDto> findReviewById(Long reviewId) {
        if (reviewId != null) {
            return albumReviewRepository.findById(reviewId).stream()
                    .map(this::buildAlbumReviewDtoFromEntity)
                    .findFirst();
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void deleteReview(Long albumReviewId) {
        if (albumReviewId != null) {
            Optional<AlbumReview> maybeAlbumReview = albumReviewRepository
                    .findById(albumReviewId);
            maybeAlbumReview.ifPresent(albumReview -> {
                albumReviewRepository.delete(albumReview);
                log.debug(LoggingUtils.ALBUM_REVIEW_WAS_DELETED_SERVICE, albumReview);
            });
        }
    }

    @Transactional
    @Override
    public void updateReview(AlbumReviewDto albumReviewDto) {
        if (albumReviewDto != null) {
            Optional<AlbumReview> maybeAlbumReview = albumReviewRepository
                    .findById(albumReviewDto.getId());
            maybeAlbumReview.ifPresent(albumReview -> {
                albumReviewRepository.update(albumReview);
                log.debug(LoggingUtils.ALBUM_REVIEW_WAS_UPDATED_SERVICE, albumReview);
            });
        }
    }

    @Override
    public List<AlbumReviewDto> findReviewsByUserLogin(String login) {
        if (login != null) {
            return albumReviewRepository.findAlbumReviewsByUserLogin(login).stream()
                    .map(this::buildAlbumReviewDtoFromEntity)
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<AlbumReviewDto> findReviewsByUserId(Long userId) {
        if (userId != null) {
            return albumReviewRepository.findAlbumReviewsByUserId(userId).stream()
                    .map(this::buildAlbumReviewDtoFromEntity)
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<AlbumReviewDto> listReviews(int limit, int offset) {
        return albumReviewRepository.findAll(limit, offset).stream()
                .map(this::buildAlbumReviewDtoFromEntity)
                .toList();
    }

    private AlbumReviewDto buildAlbumReviewDtoFromEntity(AlbumReview albumReview) {
        return AlbumReviewDto.builder()
                .id(albumReview.getId())
                .reviewText(albumReview.getReviewText())
                .user(UserDto.builder()
                        .id(albumReview.getUser().getId())
                        .login(albumReview.getUser().getLogin())
                        .email(albumReview.getUser().getEmail())
                        .build())
                .album(AlbumDto.builder()
                        .id(albumReview.getAlbum().getId())
                        .title(albumReview.getAlbum().getTitle())
                        .build())
                .build();
    }
}