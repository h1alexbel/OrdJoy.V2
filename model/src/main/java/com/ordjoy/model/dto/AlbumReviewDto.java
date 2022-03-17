package com.ordjoy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AlbumReviewDto extends ReviewDto implements Serializable {

    private AlbumDto album;

    @Builder
    public AlbumReviewDto(Long id, String reviewText, UserDto user, AlbumDto album) {
        super(id, reviewText, user);
        this.album = album;
    }
}