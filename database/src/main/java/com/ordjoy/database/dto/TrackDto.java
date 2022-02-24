package com.ordjoy.database.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackDto implements Serializable {

    private Long id;
    private String title;
    private String url;
    private AlbumDto album;
    private List<MixDto> mixes;
    private List<TrackReviewDto> trackReviews;
}