package com.ordjoy.database.repository.review.impl;

import com.ordjoy.database.config.HibernateConfigTest;
import com.ordjoy.database.model.review.TrackReview;
import com.ordjoy.database.model.user.User;
import com.ordjoy.database.repository.review.TrackReviewRepository;
import com.ordjoy.database.repository.user.UserRepository;
import com.ordjoy.database.util.TestDataImporter;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
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
@ContextConfiguration(classes = HibernateConfigTest.class)
@Transactional
class TrackReviewRepositoryImplTest {

    @Autowired
    private TrackReviewRepository trackReviewRepository;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void importTestData() {
        TestDataImporter.importTestData(sessionFactory);
    }

    @AfterTestMethod
    public void flush() {
        sessionFactory.close();
    }

    @Test
    void findTrackReviewsByUserLogin() {
        List<TrackReview> reviews = trackReviewRepository.findTrackReviewsByUserLogin("johnyy66");
        assertThat(reviews).isNotEmpty();
    }

    @Test
    void findTrackReviewsByUserId() {
        Optional<User> maybeUser = userRepository.findById(1L);
        maybeUser.ifPresent(user -> assertThat(trackReviewRepository.findTrackReviewsByUserId(user.getId()))
                .isNotEmpty());
    }
}