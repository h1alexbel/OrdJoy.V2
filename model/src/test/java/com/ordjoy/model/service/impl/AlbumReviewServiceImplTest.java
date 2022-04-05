package com.ordjoy.model.service.impl;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.dto.AlbumReviewDto;
import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.service.AlbumReviewService;
import com.ordjoy.model.service.UserService;
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
class AlbumReviewServiceImplTest {

    @Autowired
    private UserService userService;
    @Autowired
    private AlbumReviewService albumReviewService;
    @Autowired
    private TestDataImporter testDataImporter;

    @BeforeEach
    public void init() {
        testDataImporter.cleanTestData();
        testDataImporter.importTestData();
    }

    @Test
    @DisplayName("find album reviews by user login")
    void findReviewsByUserLogin() {
        List<AlbumReviewDto> reviews = albumReviewService.findReviewsByUserLogin("johnyy66");
        assertThat(reviews).hasSize(2);
    }

    @Test
    @DisplayName("find album reviews by user id")
    void findReviewsByUserId() {
        Optional<UserDto> maybeUser = userService.findUserByLogin("johnyy66");
        if (maybeUser.isPresent()) {
            UserDto userDto = maybeUser.get();
            List<AlbumReviewDto> reviews = albumReviewService.findReviewsByUserId(userDto.getId());
            assertThat(reviews).hasSize(2);
        }
    }

    @Test
    @DisplayName("find all album reviews")
    void listReviews() {
        List<AlbumReviewDto> albumReviews = albumReviewService.list(20, 0);
        assertThat(albumReviews).hasSize(4);
    }
}