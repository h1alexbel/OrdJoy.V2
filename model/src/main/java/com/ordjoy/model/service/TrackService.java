package com.ordjoy.model.service;

import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.dto.TrackReviewDto;

import java.util.List;
import java.util.Optional;

public interface TrackService extends GenericCRUDService<TrackDto, Long> {

    void addTrackToMix(TrackDto trackDto, MixDto mixDto);

    Optional<TrackDto> findTrackByTitle(String title);

    boolean isTracksTitleExists(String title);

    List<TrackReviewDto> findTrackReviewsByTrackTitle(String trackTitle, int limit, int offset);

    List<TrackReviewDto> findTrackReviewsByTrackId(Long trackId);

    void deleteTrack(Long trackId);

    Long getTrackReviewWithTrackTitlePredicatePages(String trackTitle);
}