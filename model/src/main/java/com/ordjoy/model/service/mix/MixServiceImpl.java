package com.ordjoy.model.service.mix;

import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.MixReviewDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.log.LoggingUtils;
import com.ordjoy.model.repository.mix.MixRepository;
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
public class MixServiceImpl implements MixService {

    private final MixRepository mixRepository;

    @Autowired
    public MixServiceImpl(MixRepository mixRepository) {
        this.mixRepository = mixRepository;
    }

    @Override
    public List<MixDto> listMixes() {
        return mixRepository.findAll().stream()
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
                .build();
        Mix saved = mixRepository.add(mix);
        log.debug(LoggingUtils.MIX_WAS_SAVED_SERVICE, saved);
        return MixDto.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .description(saved.getDescription())
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
            Optional<Mix> maybeMix = mixRepository.findById(mixDto.getId());
            maybeMix.ifPresent(mix -> {
                mixRepository.update(mix);
                log.debug(LoggingUtils.MIX_WAS_UPDATED_SERVICE, mix);
            });
        }
    }

    @Transactional
    @Override
    public void deleteMix(MixDto mixDto) {
        if (mixDto != null) {
            Optional<Mix> maybeMix = mixRepository.findById(mixDto.getId());
            maybeMix.ifPresent(mix -> {
                mixRepository.delete(mix);
                log.debug(LoggingUtils.MIX_WAS_DELETE_SERVICE, mix);
            });
        }
    }
}