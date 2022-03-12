package com.ordjoy.service.mapper.impl;

import com.ordjoy.database.model.review.MixReview;
import com.ordjoy.service.dto.MixReviewDto;
import com.ordjoy.service.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Deprecated(since = "1.2")
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