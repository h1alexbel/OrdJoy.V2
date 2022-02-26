package com.ordjoy.service.mapper.impl;

import com.ordjoy.database.model.review.AlbumReview;
import com.ordjoy.service.dto.AlbumReviewDto;
import com.ordjoy.service.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumReviewMapper implements Mapper<AlbumReview, AlbumReviewDto> {

    private static final AlbumReviewMapper INSTANCE = new AlbumReviewMapper();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final AlbumMapper albumMapper = AlbumMapper.getInstance();

    @Override
    public AlbumReviewDto mapFrom(AlbumReview albumReview) {
        return AlbumReviewDto.builder()
                .id(albumReview.getId())
                .reviewText(albumReview.getReviewText())
                .user(userMapper.mapFrom(albumReview.getUser()))
                .album(albumMapper.mapFrom(albumReview.getAlbum()))
                .build();
    }

    public static AlbumReviewMapper getInstance() {
        return INSTANCE;
    }
}