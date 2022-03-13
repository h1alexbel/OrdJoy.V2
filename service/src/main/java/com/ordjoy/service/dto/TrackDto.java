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
@ToString(exclude = "trackReviews")
@Builder
public class TrackDto implements Serializable {

    private Long id;
    private String title;
    private String url;
    private AlbumDto album;
    private List<TrackReviewDto> trackReviews;
}