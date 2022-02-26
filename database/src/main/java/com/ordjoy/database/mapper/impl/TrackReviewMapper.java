package com.ordjoy.database.mapper.impl;

import com.ordjoy.database.dto.TrackReviewDto;
import com.ordjoy.database.mapper.Mapper;
import com.ordjoy.database.model.TrackReview;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrackReviewMapper implements Mapper<TrackReview, TrackReviewDto> {

    private static final TrackReviewMapper INSTANCE = new TrackReviewMapper();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final TrackMapper trackMapper = TrackMapper.getInstance();

    @Override
    public TrackReviewDto mapFrom(TrackReview trackReview) {
        return TrackReviewDto.builder()
                .id(trackReview.getId())
                .reviewText(trackReview.getReviewText())
                .user(userMapper.mapFrom(trackReview.getUser()))
                .track(trackMapper.mapFrom(trackReview.getTrack()))
                .build();
    }

    public static TrackReviewMapper getInstance() {
        return INSTANCE;
    }
}