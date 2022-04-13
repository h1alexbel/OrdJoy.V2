package com.ordjoy.model.repository.impl;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.entity.review.MixReview;
import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.repository.MixReviewRepository;
import com.ordjoy.model.repository.UserRepository;
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
class MixReviewRepositoryImplTest {

    @Autowired
    private MixReviewRepository mixReviewRepository;
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
    @DisplayName("find mix reviews by user's login test case")
    void findMixReviewsByUserLogin() {
        List<MixReview> reviews = mixReviewRepository
                .findMixReviewsByUserLogin("johnyy66", 20, 0);
        assertThat(reviews).isNotEmpty();
    }

    @Test
    @DisplayName("find mix reviews by user's id test case")
    void findMixReviewsByUserId() {
        Optional<User> maybeUser = userRepository.findByLogin("johnyy66");
        assertThat(maybeUser).isNotEmpty();
        maybeUser.ifPresent(user -> assertThat(mixReviewRepository.findMixReviewsByUserId(user.getId()))
                .isNotEmpty());
    }
}