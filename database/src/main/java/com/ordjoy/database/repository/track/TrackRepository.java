package com.ordjoy.database.repository.track;

import com.ordjoy.database.model.track.Mix;
import com.ordjoy.database.model.track.Track;

import java.util.List;
import java.util.Optional;

public interface TrackRepository {

    void addTrackToMix(Track track, Mix mix);

    Optional<Track> findTrackByTitle(String title);

    List<Track> findTracksByAlbumId(Long albumId);

    List<Track> findTracksByAlbumName(String albumName);
}