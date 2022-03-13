package com.ordjoy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"tracks", "albumReviews"})
@Builder
public class AlbumDto implements Serializable {

    private Long id;
    private String title;
    private List<TrackDto> tracks;
    private List<AlbumReviewDto> albumReviews;
}