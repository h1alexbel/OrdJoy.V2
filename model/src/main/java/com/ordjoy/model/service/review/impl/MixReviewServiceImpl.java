package com.ordjoy.model.service.review.impl;

import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.MixReviewDto;
import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.entity.review.MixReview;
import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.util.LoggingUtils;
import com.ordjoy.model.repository.review.MixReviewRepository;
import com.ordjoy.model.service.review.MixReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
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
    public MixReviewDto saveReview(MixReviewDto mixReviewDto) {
        MixReview mixReviewToSave = MixReview.builder()
                .reviewText(mixReviewDto.getReviewText())
                .mix(Mix.builder()
                        .title(mixReviewDto.getMix().getTitle())
                        .description(mixReviewDto.getMix().getDescription())
                        .build())
                .user(User.builder()
                        .login(mixReviewDto.getUser().getLogin())
                        .email(mixReviewDto.getUser().getEmail())
                        .build())
                .build();
        mixReviewToSave.getUser().setId(mixReviewDto.getUser().getId());
        mixReviewToSave.getMix().setId(mixReviewDto.getMix().getId());
        MixReview savedMixReview = mixReviewRepository.add(mixReviewToSave);
        log.debug(LoggingUtils.MIX_REVIEW_WAS_ADDED_SERVICE, savedMixReview);
        return buildMixReviewDtoFromEntity(savedMixReview);
    }

    @Override
    public Optional<MixReviewDto> findReviewById(Long reviewId) {
        if (reviewId != null) {
            return mixReviewRepository.findById(reviewId).stream()
                    .map(this::buildMixReviewDtoFromEntity)
                    .findFirst();
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void deleteReview(MixReviewDto mixReviewDto) {
        if (mixReviewDto != null) {
            Optional<MixReview> maybeMixReview = mixReviewRepository
                    .findById(mixReviewDto.getId());
            maybeMixReview.ifPresent(mixReview -> {
                mixReviewRepository.delete(mixReview);
                log.debug(LoggingUtils.MIX_REVIEW_WAS_DELETED_SERVICE, mixReview);
            });
        }
    }

    @Transactional
    @Override
    public void updateReview(MixReviewDto mixReviewDto) {
        if (mixReviewDto != null) {
            Optional<MixReview> maybeMixReview = mixReviewRepository
                    .findById(mixReviewDto.getId());
            maybeMixReview.ifPresent(mixReview -> {
                mixReviewRepository.update(mixReview);
                log.debug(LoggingUtils.MIX_REVIEW_WAS_UPDATED_SERVICE, mixReview);
            });
        }
    }

    @Override
    public List<MixReviewDto> findReviewsByUserLogin(String login) {
        if (login != null) {
            return mixReviewRepository.findMixReviewsByUserLogin(login).stream()
                    .map(this::buildMixReviewDtoFromEntity)
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<MixReviewDto> findReviewsByUserId(Long userId) {
        if (userId != null) {
            return mixReviewRepository.findMixReviewsByUserId(userId).stream()
                    .map(this::buildMixReviewDtoFromEntity)
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<MixReviewDto> listReviews() {
        return mixReviewRepository.findAll().stream()
                .map(this::buildMixReviewDtoFromEntity)
                .toList();
    }

    private MixReviewDto buildMixReviewDtoFromEntity(MixReview mixReview) {
        return MixReviewDto.builder()
                .id(mixReview.getId())
                .reviewText(mixReview.getReviewText())
                .user(UserDto.builder()
                        .id(mixReview.getUser().getId())
                        .login(mixReview.getUser().getLogin())
                        .email(mixReview.getUser().getEmail())
                        .build())
                .mix(MixDto.builder()
                        .id(mixReview.getMix().getId())
                        .title(mixReview.getMix().getTitle())
                        .description(mixReview.getMix().getDescription())
                        .build())
                .build();
    }
}