package com.ordjoy.database.dto;

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
public class MixReviewDto extends ReviewDto implements Serializable {

    private MixDto mix;

    @Builder
    public MixReviewDto(Long id, String reviewText, UserDto user, MixDto mix) {
        super(id, reviewText, user);
        this.mix = mix;
    }
}