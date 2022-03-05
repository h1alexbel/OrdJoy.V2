package com.ordjoy.database.repository.track;

import com.ordjoy.database.model.track.Mix;
import com.ordjoy.database.model.track.Track;
import com.ordjoy.database.repository.GenericCRUDRepository;

import java.util.List;
import java.util.Optional;

public interface TrackRepository extends GenericCRUDRepository<Track, Long> {

    void addTrackToMix(Track track, Mix mix);

    Optional<Track> findByTitle(String title);

    List<Track> findTracksByAlbumId(Long albumId);

    List<Track> findTracksByAlbumName(String albumName);
}