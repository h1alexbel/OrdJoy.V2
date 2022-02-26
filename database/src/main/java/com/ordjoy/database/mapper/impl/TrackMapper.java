package com.ordjoy.database.mapper.impl;

import com.ordjoy.database.dto.TrackDto;
import com.ordjoy.database.mapper.Mapper;
import com.ordjoy.database.model.Track;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrackMapper implements Mapper<Track, TrackDto> {

    private static final TrackMapper INSTANCE = new TrackMapper();

    @Override
    public TrackDto mapFrom(Track track) {
        return TrackDto.builder()
                .id(track.getId())
                .title(track.getTitle())
                .url(track.getUrl())
                .build();
    }

    public static TrackMapper getInstance() {
        return INSTANCE;
    }
}