package com.ordjoy.model.service;

import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.MixReviewDto;
import com.ordjoy.model.dto.TrackDto;

import java.util.List;
import java.util.Optional;

public interface MixService extends GenericCRUDService<MixDto, Long> {

    Optional<MixDto> findMixByTitle(String title);

    boolean isMixTitleExists(String title);

    List<TrackDto> findTracksByMixTitle(String mixTitle);

    List<MixReviewDto> findMixReviewsByMixTitle(String mixTitle);

    List<MixReviewDto> findMixReviewsByMixId(Long mixId);

    void deleteMix(Long mixId);
}