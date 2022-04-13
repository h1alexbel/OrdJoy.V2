package com.ordjoy.model.service;

import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.MixReviewDto;
import com.ordjoy.model.dto.TrackDto;

import java.util.List;
import java.util.Optional;

public interface MixService extends GenericCRUDService<MixDto, Long> {

    Optional<MixDto> findMixByTitle(String title);

    boolean isMixTitleExists(String title);

    List<TrackDto> findTracksByMixTitle(String mixTitle, int limit, int offset);

    List<MixReviewDto> findMixReviewsByMixTitle(String mixTitle, int limit, int offset);

    List<MixReviewDto> findMixReviewsByMixId(Long mixId);

    void deleteMix(Long mixId);

    Long getMixReviewWithMixTitlePredicatePages(String mixTitle);

    Long getTrackWithMixTitlePredicatePages(String mixTitle);
}