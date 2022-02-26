package com.ordjoy.database.mapper.impl;

import com.ordjoy.database.dto.MixDto;
import com.ordjoy.database.dto.TrackDto;
import com.ordjoy.database.mapper.Mapper;
import com.ordjoy.database.model.Mix;
import com.ordjoy.database.model.Track;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MixMapper implements Mapper<Mix, MixDto> {

    private static final MixMapper INSTANCE = new MixMapper();
    private final TrackMapper trackMapper = TrackMapper.getInstance();

    @Override
    public MixDto mapFrom(Mix mix) {
        List<TrackDto> tracksDto = getTracksDto(mix);
        return MixDto.builder()
                .id(mix.getId())
                .title(mix.getTitle())
                .description(mix.getDescription())
                .tracks(tracksDto)
                .build();
    }

    private List<TrackDto> getTracksDto(Mix mix) {
        List<TrackDto> tracksDto = new ArrayList<>();
        for (Track track : mix.getTracks()) {
            tracksDto.add(trackMapper.mapFrom(track));
        }
        return tracksDto;
    }

    public static MixMapper getInstance() {
        return INSTANCE;
    }
}