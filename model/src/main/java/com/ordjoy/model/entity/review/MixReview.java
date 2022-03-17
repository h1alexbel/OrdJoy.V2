package com.ordjoy.model.entity.review;

import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@DiscriminatorValue("mix_review")
public class MixReview extends Review {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mix_id")
    private Mix mix;

    @Builder
    public MixReview(String reviewText, User user, Mix mix) {
        super(reviewText, user);
        this.mix = mix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MixReview mixReview = (MixReview) o;
        return id != null && Objects.equals(id, mixReview.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}