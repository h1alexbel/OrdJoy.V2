package com.ordjoy.model.repository.track;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.entity.review.TrackReview;
import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.repository.mix.MixRepository;
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
class TrackRepositoryImplTest {

    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private MixRepository mixRepository;
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
    @DisplayName("add existing track to existing mix")
    void addTrackToMix() {
        Optional<Track> maybeTrack = trackRepository.findById(1L);
        Optional<Mix> maybeMix = mixRepository.findById(1L);
        if (maybeMix.isPresent() && maybeTrack.isPresent()) {
            Mix mix = maybeMix.get();
            Track track = maybeTrack.get();
            trackRepository.addTrackToMix(track, mix);
            assertThat(mix.getTracks()).isNotEmpty().hasSize(1);
        }
    }

    @Test
    @DisplayName("find track by track title test case")
    void findByTitle() {
        Optional<Track> maybeTrack = trackRepository.findByTitle("Jail");
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
        Optional<Track> byId = trackRepository.findById(6L);
        byId.ifPresent(user -> assertThat(trackRepository.findTrackReviewsByTrackId(6L))
                .isNotEmpty());
    }
}