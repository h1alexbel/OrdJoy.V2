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
public class TrackReviewDto extends ReviewDto implements Serializable {

    private TrackDto track;

    @Builder
    public TrackReviewDto(Long id, String reviewText, UserDto user, TrackDto track) {
        super(id, reviewText, user);
        this.track = track;
    }
}