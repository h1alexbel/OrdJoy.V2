package com.ordjoy.service.service.review.impl;

import com.ordjoy.database.model.review.AlbumReview;
import com.ordjoy.database.repository.review.AlbumReviewRepository;
import com.ordjoy.service.dto.AlbumDto;
import com.ordjoy.service.dto.AlbumReviewDto;
import com.ordjoy.service.dto.UserDto;
import com.ordjoy.service.service.review.AlbumReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public AlbumReviewDto saveReview(AlbumReview albumReview) {
        AlbumReview savedReview = albumReviewRepository.add(albumReview);
        return AlbumReviewDto.builder()
                .id(savedReview.getId())
                .reviewText(savedReview.getReviewText())
                .user(UserDto.builder()
                        .id(savedReview.getUser().getId())
                        .login(savedReview.getUser().getLogin())
                        .build())
                .album(AlbumDto.builder()
                        .id(savedReview.getAlbum().getId())
                        .title(savedReview.getAlbum().getTitle())
                        .build())
                .build();
    }

    @Override
    public Optional<AlbumReviewDto> findReviewById(Long reviewId) {
        if (reviewId != null) {
            return albumReviewRepository.findById(reviewId).stream()
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
                    .findFirst();
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void deleteReview(AlbumReview albumReview) {
        if (albumReview != null) {
            albumReviewRepository.delete(albumReview);
        }
    }

    @Transactional
    @Override
    public void updateReview(AlbumReview albumReview) {
        if (albumReview != null) {
            albumReviewRepository.update(albumReview);
        }
    }

    @Override
    public List<AlbumReviewDto> findReviewsByUserLogin(String login) {
        if (login != null) {
            return albumReviewRepository.findAlbumReviewsByUserLogin(login).stream()
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
    public List<AlbumReviewDto> findReviewsByUserId(Long userId) {
        if (userId != null) {
            return albumReviewRepository.findAlbumReviewsByUserId(userId).stream()
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
    public List<AlbumReviewDto> listReviews() {
        return albumReviewRepository.findAll().stream()
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
}