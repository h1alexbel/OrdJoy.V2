package com.ordjoy.service.service.review.impl;

import com.ordjoy.database.model.review.MixReview;
import com.ordjoy.database.repository.review.MixReviewRepository;
import com.ordjoy.service.dto.MixDto;
import com.ordjoy.service.dto.MixReviewDto;
import com.ordjoy.service.dto.UserDto;
import com.ordjoy.service.service.review.MixReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MixReviewServiceImpl implements MixReviewService {

    private final MixReviewRepository mixReviewRepository;

    @Autowired
    public MixReviewServiceImpl(MixReviewRepository mixReviewRepository) {
        this.mixReviewRepository = mixReviewRepository;
    }

    @Transactional
    @Override
    public MixReviewDto saveReview(MixReview review) {
        MixReview savedReview = mixReviewRepository.add(review);
        return MixReviewDto.builder()
                .id(savedReview.getId())
                .reviewText(savedReview.getReviewText())
                .user(UserDto.builder()
                        .id(savedReview.getUser().getId())
                        .login(savedReview.getUser().getLogin())
                        .build())
                .mix(MixDto.builder()
                        .id(savedReview.getMix().getId())
                        .title(savedReview.getMix().getTitle())
                        .description(savedReview.getMix().getDescription())
                        .build())
                .build();
    }

    @Override
    public Optional<MixReviewDto> findReviewById(Long reviewId) {
        if (reviewId != null) {
            return mixReviewRepository.findById(reviewId).stream()
                    .map(mixReview -> MixReviewDto.builder()
                            .id(mixReview.getId())
                            .reviewText(mixReview.getReviewText())
                            .user(UserDto.builder()
                                    .id(mixReview.getUser().getId())
                                    .login(mixReview.getUser().getLogin())
                                    .build())
                            .mix(MixDto.builder()
                                    .id(mixReview.getMix().getId())
                                    .title(mixReview.getMix().getTitle())
                                    .description(mixReview.getMix().getDescription())
                                    .build())
                            .build())
                    .findFirst();
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void deleteReview(MixReview mixReview) {
        if (mixReview != null) {
            mixReviewRepository.delete(mixReview);
        }
    }

    @Transactional
    @Override
    public void updateReview(MixReview mixReview) {
        if (mixReview != null) {
            mixReviewRepository.update(mixReview);
        }
    }

    @Override
    public List<MixReviewDto> findReviewsByUserLogin(String login) {
        if (login != null) {
            return mixReviewRepository.findMixReviewsByUserLogin(login).stream()
                    .map(mixReview -> MixReviewDto.builder()
                            .id(mixReview.getId())
                            .reviewText(mixReview.getReviewText())
                            .user(UserDto.builder()
                                    .id(mixReview.getUser().getId())
                                    .login(mixReview.getUser().getLogin())
                                    .build())
                            .mix(MixDto.builder()
                                    .id(mixReview.getMix().getId())
                                    .title(mixReview.getMix().getTitle())
                                    .description(mixReview.getMix().getDescription())
                                    .build())
                            .build())
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<MixReviewDto> findReviewsByUserId(Long userId) {
        if (userId != null) {
            return mixReviewRepository.findMixReviewsByUserId(userId).stream()
                    .map(mixReview -> MixReviewDto.builder()
                            .id(mixReview.getId())
                            .reviewText(mixReview.getReviewText())
                            .user(UserDto.builder()
                                    .id(mixReview.getUser().getId())
                                    .login(mixReview.getUser().getLogin())
                                    .build())
                            .mix(MixDto.builder()
                                    .id(mixReview.getMix().getId())
                                    .title(mixReview.getMix().getTitle())
                                    .description(mixReview.getMix().getDescription())
                                    .build())
                            .build())
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<MixReviewDto> listReviews() {
        return mixReviewRepository.findAll().stream()
                .map(mixReview -> MixReviewDto.builder()
                        .id(mixReview.getId())
                        .reviewText(mixReview.getReviewText())
                        .user(UserDto.builder()
                                .id(mixReview.getUser().getId())
                                .login(mixReview.getUser().getLogin())
                                .build())
                        .mix(MixDto.builder()
                                .id(mixReview.getMix().getId())
                                .title(mixReview.getMix().getTitle())
                                .description(mixReview.getMix().getDescription())
                                .build())
                        .build())
                .toList();
    }
}