package com.ordjoy.model.entity.track;

import com.ordjoy.model.entity.BaseEntity;
import com.ordjoy.model.entity.review.AlbumReview;
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
@Table(name = "album", schema = "audio_storage")
@SQLDelete(sql = "UPDATE audio_storage.album SET state = 'NOT_ACTIVE' WHERE id = ?",
        check = ResultCheckStyle.COUNT)
@Where(clause = "state = 'ACTIVE'")
public class Album extends BaseEntity<Long> {

    @Column(length = 128, nullable = false, unique = true)
    private String title;

    @OneToMany(mappedBy = "album", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @Builder.Default
    @ToString.Exclude
    private List<Track> tracks = new ArrayList<>();

    @OneToMany(mappedBy = "album", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @Builder.Default
    @ToString.Exclude
    private List<AlbumReview> albumReviews = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Album album = (Album) o;
        return id != null && Objects.equals(id, album.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}