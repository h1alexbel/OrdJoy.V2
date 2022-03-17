package com.ordjoy.model.dto;

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
@ToString(exclude = {"tracks", "mixReviews"})
@Builder
public class MixDto implements Serializable {

    private Long id;
    private String title;
    private String description;
    private List<TrackDto> tracks;
    private List<MixReviewDto> mixReviews;
}