package com.ordjoy.service.service.mix;

import com.ordjoy.database.model.track.Mix;
import com.ordjoy.database.repository.mix.MixRepository;
import com.ordjoy.service.dto.AlbumDto;
import com.ordjoy.service.dto.MixDto;
import com.ordjoy.service.dto.MixReviewDto;
import com.ordjoy.service.dto.TrackDto;
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
    public MixDto saveMix(Mix mix) {
        Mix savedMix = mixRepository.add(mix);
        return MixDto.builder()
                .id(savedMix.getId())
                .title(savedMix.getTitle())
                .description(savedMix.getDescription())
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
        AtomicBoolean result = new AtomicBoolean(false);
        if (title != null) {
            mixRepository.findByTitle(title)
                    .ifPresent(mix -> result.set(true));
        }
        return result.get();
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
    public void updateMix(Mix mix) {
        if (mix != null) {
            mixRepository.update(mix);
        }
    }

    @Transactional
    @Override
    public void deleteMix(Mix mix) {
        if (mix != null) {
            mixRepository.delete(mix);
        }
    }
}