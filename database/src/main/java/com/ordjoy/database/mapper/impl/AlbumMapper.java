package com.ordjoy.database.mapper.impl;

import com.ordjoy.database.dto.AlbumDto;
import com.ordjoy.database.dto.TrackDto;
import com.ordjoy.database.mapper.Mapper;
import com.ordjoy.database.model.Album;
import com.ordjoy.database.model.Track;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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