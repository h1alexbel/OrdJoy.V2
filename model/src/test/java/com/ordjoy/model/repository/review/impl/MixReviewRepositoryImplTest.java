package com.ordjoy.model.repository.review.impl;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.entity.review.MixReview;
import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.repository.review.MixReviewRepository;
import com.ordjoy.model.repository.user.UserRepository;
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
class MixReviewRepositoryImplTest {

    @Autowired
    private MixReviewRepository mixReviewRepository;
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
    @DisplayName("find mix reviews by user's login test case")
    void findMixReviewsByUserLogin() {
        List<MixReview> reviews = mixReviewRepository.findMixReviewsByUserLogin("johnyy66");
        assertThat(reviews).isNotEmpty();
    }

    @Test
    @DisplayName("find mix reviews by user's id test case")
    void findMixReviewsByUserId() {
        Optional<User> maybeUser = userRepository.findById(1L);
        maybeUser.ifPresent(user -> assertThat(mixReviewRepository.findMixReviewsByUserId(user.getId()))
                .isNotEmpty());
    }
}