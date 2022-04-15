package com.ordjoy.model.entity.track;

import com.ordjoy.model.entity.BaseEntity;
import com.ordjoy.model.entity.order.UserTrackOrder;
import com.ordjoy.model.entity.review.TrackReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "track", schema = "audio_storage")
@SQLDelete(sql = "UPDATE audio_storage.track SET state = 'NOT_ACTIVE' WHERE id = ?",
        check = ResultCheckStyle.COUNT)
@Where(clause = "state = 'ACTIVE'")
public class Track extends BaseEntity<Long> {

    @Column(length = 512, nullable = false, unique = true)
    private String title;

    @Column(length = 512, nullable = false, unique = true)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    @ToString.Exclude
    private Album album;

    @ManyToMany(mappedBy = "tracks")
    @Builder.Default
    @ToString.Exclude
    private List<Mix> mixes = new ArrayList<>();

    @OneToMany(mappedBy = "track")
    @Builder.Default
    @ToString.Exclude
    private List<TrackReview> trackReviews = new ArrayList<>();

    @OneToMany(mappedBy = "track")
    @Builder.Default
    @ToString.Exclude
    private List<UserTrackOrder> orders = new ArrayList<>();

    public void addReviewToTrack(TrackReview trackReview) {
        trackReviews.add(trackReview);
        trackReview.setTrack(this);
    }

    public void addOrderToTrack(UserTrackOrder order) {
        orders.add(order);
        order.setTrack(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Track track = (Track) o;
        return id != null && Objects.equals(id, track.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}