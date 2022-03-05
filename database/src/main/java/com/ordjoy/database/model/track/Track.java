package com.ordjoy.database.model.track;

import com.ordjoy.database.model.BaseEntity;
import com.ordjoy.database.model.review.TrackReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

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
public class Track extends BaseEntity<Long> {

    @Column(length = 512, nullable = false)
    private String title;

    @Column(length = 512, nullable = false, unique = true)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToMany(mappedBy = "tracks")
    @Builder.Default
    @ToString.Exclude
    private List<Mix> mixes = new ArrayList<>();

    @OneToMany(mappedBy = "track")
    @Builder.Default
    @ToString.Exclude
    private List<TrackReview> trackReviews = new ArrayList<>();

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