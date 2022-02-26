package com.ordjoy.service.mapper.impl;

import com.ordjoy.database.model.track.Track;
import com.ordjoy.service.dto.TrackDto;
import com.ordjoy.service.mapper.Mapper;
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