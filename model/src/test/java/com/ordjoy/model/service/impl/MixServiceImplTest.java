package com.ordjoy.model.service.impl;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.MixReviewDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.service.MixService;
import com.ordjoy.model.util.TestDataImporter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfigTest.class)
@Transactional
class MixServiceImplTest {

    @Autowired
    private MixService mixService;
    @Autowired
    private TestDataImporter testDataImporter;

    @BeforeEach
    public void init() {
        testDataImporter.cleanTestData();
        testDataImporter.importTestData();
    }

    @Test
    @DisplayName("find all mixes test case")
    void listMixes() {
        List<MixDto> mixes = mixService.list(10, 0);
        Assertions.assertThat(mixes).hasSize(3);
    }

    @Test
    @DisplayName("find mix by title test case")
    void findMixByTitle() {
        Optional<MixDto> maybeMix = mixService
                .findMixByTitle("Hip-Hop");
        assertThat(maybeMix).isNotEmpty();
        MixDto mixDto = maybeMix.get();
        assertThat(mixService
                .findMixByTitle(mixDto.getTitle()))
                .isNotEmpty();
    }

    @Test
    @DisplayName("find mix by id test case")
    void findMixById() {
        Optional<MixDto> mixById = mixService.findById(2L);
        mixById.ifPresent(mixDto -> assertEquals("Travis scott hits",
                mixDto.getTitle()));
    }

    @Test
    @DisplayName("find mix tracks by mix title test case")
    void findTracksByMixTitle() {
        List<TrackDto> tracksByMixTitle = mixService
                .findTracksByMixTitle("Jazz hits", 20, 0);
        assertThat(tracksByMixTitle).hasSize(1);
    }

    @Test
    @DisplayName("find mix reviews by mix title test case")
    void findMixReviewsByMixTitle() {
        List<MixReviewDto> reviewsByMixTitle = mixService
                .findMixReviewsByMixTitle("Travis scott hits", 20, 0);
        assertThat(reviewsByMixTitle).hasSize(1);
    }

    @Test
    @DisplayName("find mix reviews by mix id test case")
    void findMixReviewsByMixId() {
        Optional<MixDto> maybeMix = mixService.findMixByTitle("Hip-Hop");
        assertThat(maybeMix).isNotEmpty();
        List<MixReviewDto> reviewsByMixId = mixService
                .findMixReviewsByMixId(maybeMix.get().getId());
        assertThat(reviewsByMixId).hasSize(1);
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("find reviews by mix id null case")
    void findReviewsByMixIdNullCase(Long id) {
        assertDoesNotThrow(() -> mixService.findMixReviewsByMixId(id));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("find reviews by mix title null case")
    void findReviewsByMixTitleNullCase(String title) {
        assertDoesNotThrow(() -> mixService.findMixByTitle(title));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("delete mix null case")
    void deleteMixNullCase(Long id) {
        assertDoesNotThrow(() -> mixService.deleteMix(id));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("update mix null case")
    void updateMixNullCase(MixDto mixDto) {
        assertDoesNotThrow(() -> mixService.update(mixDto));
    }
}