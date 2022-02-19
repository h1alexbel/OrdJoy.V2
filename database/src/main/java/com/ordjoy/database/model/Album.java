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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "album", schema = "audio_storage")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false, unique = true)
    private String title;

    @OneToMany(mappedBy = "album")
    @Builder.Default
    @ToString.Exclude
    private List<Track> tracks = new ArrayList<>();

    @OneToMany(mappedBy = "album")
    @Builder.Default
    @ToString.Exclude
    private List<AlbumReview> albumReviews = new ArrayList<>();
}