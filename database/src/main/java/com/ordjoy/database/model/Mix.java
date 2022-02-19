package com.ordjoy.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "mix", schema = "audio_storage")
public class Mix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private Set<Track> tracks = new HashSet<>();

    @OneToMany(mappedBy = "mix")
    @ToString.Exclude
    @Builder.Default
    private List<MixReview> mixReviews = new ArrayList<>();
}