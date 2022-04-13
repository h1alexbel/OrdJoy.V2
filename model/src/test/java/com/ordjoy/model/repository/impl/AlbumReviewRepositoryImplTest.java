package com.ordjoy.model.repository.impl;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.entity.review.AlbumReview;
import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.repository.AlbumReviewRepository;
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
class AlbumReviewRepositoryImplTest {

    @Autowired
    private AlbumReviewRepository albumReviewRepository;
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
    @DisplayName("find album reviews by user's login test case")
    void findAlbumReviewsByUserLogin() {
        List<AlbumReview> reviews = albumReviewRepository
                .findAlbumReviewsByUserLogin("johnyy66", 20, 0);
        assertThat(reviews).isNotEmpty();
    }

    @Test
    @DisplayName("find album reviews by user's id test case")
    void findAlbumReviewsByUserId() {
        Optional<User> maybeUser = userRepository.findByLogin("johnyy66");
        assertThat(maybeUser).isNotEmpty();
        maybeUser.ifPresent(user -> assertThat(albumReviewRepository.findAlbumReviewsByUserId(user.getId()))
                .isNotEmpty());
    }
}