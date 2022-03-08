package com.ordjoy.database.repository.mix;

import com.ordjoy.database.config.HibernateConfigTest;
import com.ordjoy.database.model.review.MixReview;
import com.ordjoy.database.model.track.Mix;
import com.ordjoy.database.model.track.Track;
import com.ordjoy.database.util.TestDataImporter;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateConfigTest.class)
@Transactional
class MixRepositoryImplTest {

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
    @DisplayName("find mix by title test case")
    void findByTitle() {
        Optional<Mix> maybeMix = mixRepository.findByTitle("Travis scott hits");
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
        Optional<Mix> maybeMix = mixRepository.findById(1L);
        maybeMix.ifPresent(mix -> assertThat(mixRepository.findMixReviewsByMixId(mix.getId()))
                .isNotEmpty());
    }
}