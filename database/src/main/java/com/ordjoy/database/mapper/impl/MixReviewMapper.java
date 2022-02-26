package com.ordjoy.database.mapper.impl;

import com.ordjoy.database.dto.MixReviewDto;
import com.ordjoy.database.mapper.Mapper;
import com.ordjoy.database.model.MixReview;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MixReviewMapper implements Mapper<MixReview, MixReviewDto> {

    private static final MixReviewMapper INSTANCE = new MixReviewMapper();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final MixMapper mixMapper = MixMapper.getInstance();

    @Override
    public MixReviewDto mapFrom(MixReview mixReview) {
        return MixReviewDto.builder()
                .id(mixReview.getId())
                .reviewText(mixReview.getReviewText())
                .user(userMapper.mapFrom(mixReview.getUser()))
                .mix(mixMapper.mapFrom(mixReview.getMix()))
                .build();
    }

    public static MixReviewMapper getInstance() {
        return INSTANCE;
    }
}