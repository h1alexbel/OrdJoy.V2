package com.ordjoy.model.service.impl;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.dto.MixReviewDto;
import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.service.MixReviewService;
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
class MixReviewServiceImplTest {

    @Autowired
    private UserService userService;
    @Autowired
    private MixReviewService mixReviewService;
    @Autowired
    private TestDataImporter testDataImporter;

    @BeforeEach
    public void init() {
        testDataImporter.cleanTestData();
        testDataImporter.importTestData();
    }

    @Test
    @DisplayName("find mix reviews by user login test case")
    void findReviewsByUserLogin() {
        List<MixReviewDto> reviews = mixReviewService
                .findReviewsByUserLogin("johnyy66", 20, 0);
        assertThat(reviews).hasSize(1);
    }

    @Test
    @DisplayName("find mix reviews by user id test case")
    void findReviewsByUserId() {
        Optional<UserDto> maybeUser = userService.findUserByLogin("johnyy66");
        if (maybeUser.isPresent()) {
            UserDto userDto = maybeUser.get();
            List<MixReviewDto> reviews = mixReviewService.findReviewsByUserId(userDto.getId());
            assertThat(reviews).hasSize(1);
        }
    }

    @Test
    @DisplayName("find all mix reviews")
    void listReviews() {
        List<MixReviewDto> reviews = mixReviewService.list(20, 0);
        assertThat(reviews).hasSize(2);
    }
}