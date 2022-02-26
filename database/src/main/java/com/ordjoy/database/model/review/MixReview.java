package com.ordjoy.database.model.review;

import com.ordjoy.database.model.track.Mix;
import com.ordjoy.database.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@DiscriminatorValue("mix_review")
public class MixReview extends Review<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mix_id")
    private Mix mix;

    @Builder
    public MixReview(Long id, String reviewText, User user, Mix mix) {
        super(id, reviewText, user);
        this.mix = mix;
    }
}