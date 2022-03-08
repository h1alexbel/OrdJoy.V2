package com.ordjoy.service.service.mix;

import com.ordjoy.database.model.track.Mix;
import com.ordjoy.service.dto.MixDto;
import com.ordjoy.service.dto.MixReviewDto;
import com.ordjoy.service.dto.TrackDto;

import java.util.List;
import java.util.Optional;

public interface MixService {

    List<MixDto> listMixes();

    MixDto saveMix(Mix mix);

    Optional<MixDto> findMixByTitle(String title);

    Optional<MixDto> findMixById(Long id);

    boolean isMixTitleExists(String title);

    List<TrackDto> findTracksByMixTitle(String mixTitle);

    List<MixReviewDto> findMixReviewsByMixTitle(String mixTitle);

    List<MixReviewDto> findMixReviewsByMixId(Long mixId);

    void updateMix(Mix mix);

    void deleteMixById(Long mixId);
}