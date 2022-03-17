package com.ordjoy.model.entity.track;

import com.ordjoy.model.entity.BaseEntity;
import com.ordjoy.model.entity.review.MixReview;
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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "mix", schema = "audio_storage")
@SQLDelete(sql = "UPDATE audio_storage.mix SET state = 'NOT_ACTIVE' WHERE id = ?",
        check = ResultCheckStyle.COUNT)
@Where(clause = "state = 'ACTIVE'")
public class Mix extends BaseEntity<Long> {

    @Column(length = 128, nullable = false, unique = true)
    private String title;

    @Column(length = 512, nullable = false)
    private String description;

    @ManyToMany
    @JoinTable(
            name = "mix_tracks",
            schema = "audio_storage",
            joinColumns = @JoinColumn(name = "mix_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id"))
    @ToString.Exclude
    @Builder.Default
    private List<Track> tracks = new ArrayList<>();

    @OneToMany(mappedBy = "mix", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @ToString.Exclude
    @Builder.Default
    private List<MixReview> mixReviews = new ArrayList<>();

    public void addTrack(Track track) {
        tracks.add(track);
        track.getMixes().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Mix mix = (Mix) o;
        return id != null && Objects.equals(id, mix.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}