package com.ordjoy.model.service.mix;

import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.MixReviewDto;
import com.ordjoy.model.dto.TrackDto;

import java.util.List;
import java.util.Optional;

public interface MixService {

    List<MixDto> listMixes();

    MixDto saveMix(MixDto mixDto);

    Optional<MixDto> findMixByTitle(String title);

    Optional<MixDto> findMixById(Long id);

    boolean isMixTitleExists(String title);

    List<TrackDto> findTracksByMixTitle(String mixTitle);

    List<MixReviewDto> findMixReviewsByMixTitle(String mixTitle);

    List<MixReviewDto> findMixReviewsByMixId(Long mixId);

    void updateMix(MixDto mixDto);

    void deleteMix(MixDto mixDto);
}