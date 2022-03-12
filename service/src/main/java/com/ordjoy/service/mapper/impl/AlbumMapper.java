package com.ordjoy.service.mapper.impl;

import com.ordjoy.database.model.track.Album;
import com.ordjoy.database.model.track.Track;
import com.ordjoy.service.dto.AlbumDto;
import com.ordjoy.service.dto.TrackDto;
import com.ordjoy.service.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Deprecated(since = "1.2")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumMapper implements Mapper<Album, AlbumDto> {

    private static final AlbumMapper INSTANCE = new AlbumMapper();
    private final TrackMapper trackMapper = TrackMapper.getInstance();

    @Override
    public AlbumDto mapFrom(Album album) {
        List<TrackDto> tracksDto = getTracksDto(album);
        return AlbumDto.builder()
                .id(album.getId())
                .title(album.getTitle())
                .tracks(tracksDto)
                .build();
    }

    private List<TrackDto> getTracksDto(Album album) {
        List<TrackDto> tracksDto = new ArrayList<>();
        for (Track track : album.getTracks()) {
            tracksDto.add(trackMapper.mapFrom(track));
        }
        return tracksDto;
    }

    public static AlbumMapper getInstance() {
        return INSTANCE;
    }
}