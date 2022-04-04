package com.ordjoy.model.repository.impl;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.entity.review.TrackReview;
import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.repository.MixRepository;
import com.ordjoy.model.repository.TrackRepository;
import com.ordjoy.model.util.TestDataImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfigTest.class)
@Transactional
class TrackRepositoryImplTest {

    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private MixRepository mixRepository;
    @Autowired
    private TestDataImporter testDataImporter;

    @BeforeEach
    public void init() {
        testDataImporter.cleanTestData();
        testDataImporter.importTestData();
    }

    @Test
    @DisplayName("add existing track to existing mix")
    void addTrackToMix() {
        Optional<Track> maybeTrack = trackRepository.findByTitle("Paranoid");
        assertThat(maybeTrack).isNotEmpty();
        Optional<Mix> maybeMix = mixRepository.findByTitle("Hip-Hop");
        assertThat(maybeMix).isNotEmpty();
        Mix mix = maybeMix.get();
        Track track = maybeTrack.get();
        trackRepository.addTrackToMix(track, mix);
        assertThat(mix.getTracks()).isNotEmpty().hasSize(1);
    }

    @Test
    @DisplayName("find track by track title test case")
    void findByTitle() {
        Optional<Track> maybeTrack = trackRepository.findByTitle("Jail");
        assertThat(maybeTrack).isNotEmpty();
        maybeTrack.ifPresent(track -> assertThat(track.getTitle())
                .isEqualTo("Jail"));
    }

    @Test
    @DisplayName("find track reviews by track title test case")
    void findTrackReviewsByTrackTitle() {
        List<TrackReview> reviews = trackRepository.findTrackReviewsByTrackTitle("Paranoid");
        assertThat(reviews).isNotEmpty();
    }

    @Test
    @DisplayName("find track reviews by track id test case")
    void findTrackReviewsByTrackId() {
        Optional<Track> maybeTrack = trackRepository.findByTitle("Paranoid");
        assertThat(maybeTrack).isNotEmpty();
        maybeTrack.ifPresent(track -> assertThat(trackRepository.findTrackReviewsByTrackId(track.getId()))
                .isNotEmpty());
    }
}