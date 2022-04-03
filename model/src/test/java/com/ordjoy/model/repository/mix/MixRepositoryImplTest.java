package com.ordjoy.model.repository.mix;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.entity.review.MixReview;
import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.entity.track.Track;
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
class MixRepositoryImplTest {

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
    @DisplayName("find mix by title test case")
    void findByTitle() {
        Optional<Mix> maybeMix = mixRepository.findByTitle("Travis scott hits");
        assertThat(maybeMix).isNotEmpty();
        maybeMix.ifPresent(mix -> assertThat(mix.getTitle())
                .isEqualTo("Travis scott hits"));
    }

    @Test
    @DisplayName("find tracks by mix title test case")
    void findTracksByMixTitle() {
        List<Track> tracksByMixTitle = mixRepository.findTracksByMixTitle("Jazz hits");
        assertThat(tracksByMixTitle).hasSize(1).isNotEmpty();
    }

    @Test
    @DisplayName("find mix reviews by mix name test case")
    void findMixReviewsByMixName() {
        List<MixReview> mixReviewsByMixName =
                mixRepository.findMixReviewsByMixTitle("Travis scott hits");
        assertThat(mixReviewsByMixName).isNotEmpty();
    }

    @Test
    @DisplayName("find mix reviews by mix id test case")
    void findMixReviewsByMixId() {
        Optional<Mix> maybeMix = mixRepository.findByTitle("Hip-Hop");
        assertThat(maybeMix).isNotEmpty();
        maybeMix.ifPresent(mix -> assertThat(mixRepository.findMixReviewsByMixId(mix.getId()))
                .isNotEmpty());
    }
}