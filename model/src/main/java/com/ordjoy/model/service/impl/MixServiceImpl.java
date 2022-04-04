package com.ordjoy.model.service.impl;

import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.MixReviewDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.entity.review.MixReview;
import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.repository.MixRepository;
import com.ordjoy.model.repository.MixReviewRepository;
import com.ordjoy.model.service.MixService;
import com.ordjoy.model.util.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@Transactional(readOnly = true)
public class MixServiceImpl implements MixService {

    private final MixRepository mixRepository;
    private final MixReviewRepository mixReviewRepository;

    @Autowired
    public MixServiceImpl(MixRepository mixRepository, MixReviewRepository mixReviewRepository) {
        this.mixRepository = mixRepository;
        this.mixReviewRepository = mixReviewRepository;
    }

    @Override
    public List<MixDto> listMixes(int limit, int offset) {
        return mixRepository.findAll(limit, offset).stream()
                .map(mix -> MixDto.builder()
                        .id(mix.getId())
                        .title(mix.getTitle())
                        .description(mix.getDescription())
                        .build())
                .toList();
    }

    @Transactional
    @Override
    public MixDto saveMix(MixDto mixDto) {
        Mix mix = Mix.builder()
                .title(mixDto.getTitle())
                .description(mixDto.getDescription())
                .mixReviews(new ArrayList<>())
                .tracks(new ArrayList<>())
                .build();
        Mix saved = mixRepository.add(mix);
        log.debug(LoggingUtils.MIX_WAS_SAVED_SERVICE, saved);
        return MixDto.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .description(saved.getDescription())
                .tracks(new ArrayList<>())
                .mixReviews(new ArrayList<>())
                .build();
    }

    @Override
    public Optional<MixDto> findMixByTitle(String title) {
        if (title != null) {
            return mixRepository.findByTitle(title).stream()
                    .map(mix -> MixDto.builder()
                            .id(mix.getId())
                            .title(mix.getTitle())
                            .description(mix.getDescription())
                            .build())
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public Optional<MixDto> findMixById(Long id) {
        if (id != null) {
            return mixRepository.findById(id).stream()
                    .map(mix -> MixDto.builder()
                            .id(mix.getId())
                            .title(mix.getTitle())
                            .description(mix.getDescription())
                            .build())
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public boolean isMixTitleExists(String title) {
        AtomicBoolean isMixExistsResult = new AtomicBoolean(false);
        if (title != null) {
            mixRepository.findByTitle(title)
                    .ifPresent(mix -> {
                        isMixExistsResult.set(true);
                        log.debug(LoggingUtils.IS_MIX_EXISTS_SERVICE, mix, title, isMixExistsResult);
                    });
        }
        return isMixExistsResult.get();
    }

    @Override
    public List<TrackDto> findTracksByMixTitle(String mixTitle) {
        if (mixTitle != null) {
            return mixRepository.findTracksByMixTitle(mixTitle).stream()
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
    public List<MixReviewDto> findMixReviewsByMixTitle(String mixTitle) {
        if (mixTitle != null) {
            return mixRepository.findMixReviewsByMixTitle(mixTitle).stream()
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
                                    .build())
                            .build())
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<MixReviewDto> findMixReviewsByMixId(Long mixId) {
        if (mixId != null) {
            return mixRepository.findMixReviewsByMixId(mixId).stream()
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
                                    .build())
                            .build())
                    .toList();
        }
        return Collections.emptyList();
    }

    @Transactional
    @Override
    public void updateMix(MixDto mixDto) {
        if (mixDto != null) {
            mixRepository.update(mapEntityFromDto(mixDto));
            log.debug(LoggingUtils.MIX_WAS_UPDATED_SERVICE, mixDto);
        }
    }

    @Transactional
    @Override
    public void deleteMix(Long mixId) {
        if (mixId != null) {
            Optional<Mix> maybeMix = mixRepository.findById(mixId);
            maybeMix.ifPresent(mix -> {
                List<MixReview> mixReviews = mixRepository.findMixReviewsByMixId(mixId);
                for (MixReview mixReview : mixReviews) {
                    mixReviewRepository.delete(mixReview);
                }
                mixRepository.delete(mix);
                log.debug(LoggingUtils.MIX_WAS_DELETE_SERVICE, mix);
            });
        }
    }

    private Mix mapEntityFromDto(MixDto mixDto) {
        Mix mix = Mix.builder()
                .title(mixDto.getTitle())
                .description(mixDto.getDescription())
                .build();
        mix.setId(mixDto.getId());
        return mix;
    }
}