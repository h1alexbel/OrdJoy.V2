package com.ordjoy.model.repository.review.impl;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.entity.review.TrackReview;
import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.repository.review.TrackReviewRepository;
import com.ordjoy.model.repository.user.UserRepository;
import com.ordjoy.model.util.TestDataImporter;
import org.junit.jupiter.api.BeforeEach;
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
class TrackReviewRepositoryImplTest {

    @Autowired
    private TrackReviewRepository trackReviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestDataImporter testDataImporter;

    @BeforeEach
    public void init() {
        testDataImporter.cleanTestData();
        testDataImporter.importTestData();
    }

    @Test
    void findTrackReviewsByUserLogin() {
        List<TrackReview> reviews = trackReviewRepository.findTrackReviewsByUserLogin("johnyy66");
        assertThat(reviews).isNotEmpty();
    }

    @Test
    void findTrackReviewsByUserId() {
        Optional<User> maybeUser = userRepository.findByLogin("johnyy66");
        assertThat(maybeUser).isNotEmpty();
        maybeUser.ifPresent(user -> assertThat(trackReviewRepository.findTrackReviewsByUserId(user.getId()))
                .isNotEmpty());
    }
}