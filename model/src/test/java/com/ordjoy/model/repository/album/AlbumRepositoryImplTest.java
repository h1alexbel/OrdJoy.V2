package com.ordjoy.model.repository.album;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.entity.review.AlbumReview;
import com.ordjoy.model.entity.track.Album;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.util.TestDataImporter;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfigTest.class)
@Transactional
class AlbumRepositoryImplTest {

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    public void importTestData() {
        TestDataImporter.importTestData(sessionFactory);
    }

    @AfterTestMethod
    public void flush() {
        sessionFactory.close();
    }

    @Test
    @DisplayName("find album by title test case")
    void findByTitle() {
        Optional<Album> maybeAlbum = albumRepository.findByTitle("Metalica - Black Album");
        maybeAlbum.ifPresent(album -> assertThat(album.getTitle())
                .isEqualTo("Metalica - Black Album"));
    }

    @Test
    @DisplayName("find album reviews by album title test case")
    void findAlbumReviewsByAlbumTitle() {
        List<AlbumReview> reviewsByAlbumTitle =
                albumRepository.findAlbumReviewsByAlbumTitle("Metalica - Black Album");
        assertThat(reviewsByAlbumTitle).isNotEmpty();
    }

    @Test
    @DisplayName("find album reviews by album id test case")
    void findAlbumReviewsByAlbumId() {
        Optional<Album> maybeAlbum = albumRepository.findById(1L);
        maybeAlbum.ifPresent(album -> assertThat(albumRepository.findAlbumReviewsByAlbumId(album.getId()))
                .isNotEmpty());
    }

    @Test
    @DisplayName("find Tracks by album id test case")
    void findTracksByAlbumId() {
        Optional<Album> byId = albumRepository.findById(1L);
        byId.ifPresent(album -> assertThat(albumRepository.findTracksByAlbumId(album.getId()))
                .isNotEmpty());
    }

    @Test
    @DisplayName("find tracks by album name test case")
    void findTracksByAlbumName() {
        List<Track> tracks = albumRepository.findTracksByAlbumTitle("Post Malone - Beerbongs & Bentleys");
        assertThat(tracks).isNotEmpty();
    }
}