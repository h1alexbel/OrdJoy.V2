package com.ordjoy.database.repository.review.impl;

import com.ordjoy.database.config.HibernateConfigTest;
import com.ordjoy.database.model.review.AlbumReview;
import com.ordjoy.database.model.user.User;
import com.ordjoy.database.repository.review.AlbumReviewRepository;
import com.ordjoy.database.repository.user.UserRepository;
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

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateConfigTest.class)
@Transactional
class AlbumReviewRepositoryImplTest {

    @Autowired
    private AlbumReviewRepository albumReviewRepository;
    @Autowired
    private UserRepository userRepository;
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
    @DisplayName("find album reviews by user's login test case")
    void findAlbumReviewsByUserLogin() {
        List<AlbumReview> reviews = albumReviewRepository.findAlbumReviewsByUserLogin("johnyy66");
        assertThat(reviews).isNotEmpty();
    }

    @Test
    @DisplayName("find album reviews by user's id test case")
    void findAlbumReviewsByUserId() {
        Optional<User> maybeUser = userRepository.findById(1L);
        maybeUser.ifPresent(user -> assertThat(albumReviewRepository.findAlbumReviewsByUserId(user.getId()))
                .isNotEmpty());
    }
}