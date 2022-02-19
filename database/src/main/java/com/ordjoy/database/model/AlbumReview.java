package com.ordjoy.database.model;

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
@DiscriminatorValue("album_review")
public class AlbumReview extends Review<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "album_id")
    private Album album;

    @Builder
    public AlbumReview(Long id, String reviewText, User user, Album album) {
        super(id, reviewText, user);
        this.album = album;
    }
}