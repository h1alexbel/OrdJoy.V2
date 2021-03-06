package com.ordjoy.model.service.impl;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.service.AlbumService;
import com.ordjoy.model.util.TestDataImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
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
class AlbumServiceImplTest {

    @Autowired
    private AlbumService albumService;
    @Autowired
    private TestDataImporter testDataImporter;

    @BeforeEach
    public void init() {
        testDataImporter.cleanTestData();
        testDataImporter.importTestData();
    }

    @Test
    @DisplayName("all albums test case")
    void listAlbums() {
        List<AlbumDto> albums = albumService.list(10, 1);
        assertThat(albums).hasSize(3);
    }

    @Test
    @DisplayName("save new album test case")
    void saveAlbum() {
        AlbumDto albumDto = AlbumDto.builder()
                .title("was added by test")
                .build();
        albumService.save(albumDto);
        assertThat(albumService.findAlbumByTitle("was added by test"))
                .isNotEmpty();
    }

    @Test
    @DisplayName("find album by title default test case")
    void findAlbumByTitle() {
        Optional<AlbumDto> albumByTitle = albumService
                .findAlbumByTitle("Post Malone - Beerbongs & Bentleys");
        assertThat(albumByTitle).isNotEmpty();
        AlbumDto albumDto = albumByTitle.get();
        assertThat(albumService
                .findAlbumByTitle(albumDto.getTitle()))
                .isNotEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("find album by title null and empty case")
    void findAlbumByIdNullAndEmptyCase(String title) {
        assertDoesNotThrow(() -> albumService.findAlbumByTitle(title));
    }

    @Test
    @DisplayName("find album by id default test case")
    void findAlbumById() {
        Optional<AlbumDto> albumById = albumService.findById(2L);
        albumById.ifPresent(albumDto -> assertEquals("Metalica - Black Album",
                albumDto.getTitle()));
    }

    @Test
    @DisplayName("is album exists default test case")
    void isAlbumTitleExists() {
        boolean test = albumService.isAlbumTitleExists("was added by test");
        assertThat(test).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isAlbumTitleExistsNullAndEmptyCase(String title) {
        assertDoesNotThrow(() -> albumService.isAlbumTitleExists(title));
    }

    @Test
    @DisplayName("find album reviews by album title default test case")
    void findAlbumReviewsByAlbumTitle() {
        Optional<AlbumDto> maybeAlbumDto = albumService
                .findAlbumByTitle("Metalica - Black Album");
        maybeAlbumDto.ifPresent(album -> assertThat(albumService.
                findAlbumReviewsByAlbumTitle(album.getTitle(), 20, 0))
                .isNotEmpty().hasSize(1));
    }

    @Test
    @DisplayName("find album reviews by album id default test case")
    void findAlbumReviewsByAlbumId() {
        Optional<AlbumDto> maybeAlbumDto = albumService
                .findAlbumByTitle("Metalica - Black Album");
        maybeAlbumDto.ifPresent(album -> assertThat(albumService.
                findAlbumReviewsByAlbumId(album.getId()))
                .isNotEmpty().hasSize(1));
    }

    @Test
    @DisplayName("find tracks by album title default test case")
    void findTracksByAlbumTitle() {
        Optional<AlbumDto> maybeAlbumDto = albumService
                .findAlbumByTitle("Metalica - Black Album");
        maybeAlbumDto.ifPresent(album -> assertThat(albumService.
                findTracksByAlbumTitle(album.getTitle(), 20, 0))
                .isNotEmpty().hasSize(2));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("find tracks by album title null and empty case")
    void findTracksByAlbumTitleNullAndEmptyCase(String title) {
        assertDoesNotThrow(() -> albumService.findTracksByAlbumTitle(title, 20, 0));
    }

    @Test
    @DisplayName("find tracks by album id default test case")
    void findTracksByAlbumId() {
        Optional<AlbumDto> maybeAlbumDto = albumService
                .findAlbumByTitle("Metalica - Black Album");
        maybeAlbumDto.ifPresent(album -> assertThat(albumService.
                findTracksByAlbumId(album.getId()))
                .isNotEmpty().hasSize(2));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("find tracks by album id null case")
    void findTracksByAlbumIdNullCase(Long id) {
        assertDoesNotThrow(() -> albumService.findTracksByAlbumId(id));
    }

    @Test
    @DisplayName("update album default test case")
    void updateAlbum() {
        Optional<AlbumDto> albumByTitle = albumService.findAlbumByTitle("Metalica - Black Album");
        albumByTitle.ifPresent(albumDto -> {
            albumDto.setTitle("after update title");
            albumService.update(albumDto);
        });
        Optional<AlbumDto> afterUpdate = albumService.findById(2L);
        afterUpdate.ifPresent(albumDto -> assertEquals("after update title",
                albumDto.getTitle()));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("update album null test case")
    void updateAlbumNullCase(AlbumDto albumDto) {
        assertDoesNotThrow(() -> albumService.update(albumDto));
    }

    @Test
    @DisplayName("delete album default test case")
    void deleteAlbum() {
        Optional<AlbumDto> albumById = albumService.findById(1L);
        albumById.ifPresent(albumDto -> albumService.delete(albumDto.getId()));
        Optional<AlbumDto> albumAfterDelete = albumService.findById(1L);
        assertThat(albumAfterDelete).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("delete album null case")
    void deleteAlbumNullCase(Long id) {
        assertDoesNotThrow(() -> albumService.delete(id));
    }
}