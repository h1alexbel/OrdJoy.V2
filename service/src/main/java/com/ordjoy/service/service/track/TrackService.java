package com.ordjoy.service.service.track;

import com.ordjoy.database.model.track.Mix;
import com.ordjoy.database.model.track.Track;
import com.ordjoy.service.dto.TrackDto;
import com.ordjoy.service.dto.TrackReviewDto;

import java.util.List;
import java.util.Optional;

public interface TrackService {

    List<TrackDto> listTracks();

    TrackDto saveTrack(Track track);

    void addTrackToMix(Track track, Mix mix);

    Optional<TrackDto> findTrackById(Long id);

    Optional<TrackDto> findTrackByTitle(String title);

    boolean isTracksTitleExists(String title);

    List<TrackReviewDto> findTrackReviewsByTrackTitle(String trackTitle);

    List<TrackReviewDto> findTrackReviewsByTrackId(Long trackId);

    void updateTrack(Track track);

    void deleteTrackById(Long id);
}