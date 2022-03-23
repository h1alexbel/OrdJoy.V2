package com.ordjoy.model.service.track;

import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.dto.TrackReviewDto;

import java.util.List;
import java.util.Optional;

public interface TrackService {

    List<TrackDto> listTracks();

    TrackDto saveTrack(TrackDto trackDto);

    void addTrackToMix(TrackDto trackDto, MixDto mixDto);

    Optional<TrackDto> findTrackById(Long id);

    Optional<TrackDto> findTrackByTitle(String title);

    boolean isTracksTitleExists(String title);

    List<TrackReviewDto> findTrackReviewsByTrackTitle(String trackTitle);

    List<TrackReviewDto> findTrackReviewsByTrackId(Long trackId);

    void updateTrack(TrackDto trackDto);

    void deleteTrack(Long trackId);
}