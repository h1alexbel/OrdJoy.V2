package com.ordjoy.model.service.album;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.util.TestDataImporter;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfigTest.class)
@Transactional
class AlbumServiceImplTest {

    @Autowired
    private AlbumService albumService;
    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    public void importTestData() {
        TestDataImporter.importTestData(sessionFactory);
    }

    @AfterTestMethod
    public void flushTestData() {
        sessionFactory.close();
    }

    @Test
    @DisplayName("all albums test case")
    void listAlbums() {
        List<AlbumDto> albums = albumService.listAlbums(10, 1);
        assertThat(albums).hasSize(3);
    }

    @Test
    @DisplayName("save new album test case")
    void saveAlbum() {
        AlbumDto albumDto = AlbumDto.builder()
                .title("was added by test")
                .build();
        albumService.saveAlbum(albumDto);
        assertThat(albumService.findAlbumByTitle("was added by test"))
                .isNotEmpty();
    }

    @Test
    @DisplayName("find album by title default test case")
    void findAlbumByTitle() {
        Optional<AlbumDto> albumByTitle = albumService
                .findAlbumByTitle("Post Malone - Beerbongs & Bentleys");
        if (albumByTitle.isPresent()) {
            AlbumDto albumDto = albumByTitle.get();
            assertThat(albumService
                    .findAlbumByTitle(albumDto.getTitle()))
                    .isNotEmpty();
        } else {
            fail();
        }
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
        Optional<AlbumDto> albumById = albumService.findAlbumById(2L);
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
                findAlbumReviewsByAlbumTitle(album.getTitle()))
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
                findTracksByAlbumTitle(album.getTitle()))
                .isNotEmpty().hasSize(2));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("find tracks by album title null and empty case")
    void findTracksByAlbumTitleNullAndEmptyCase(String title) {
        assertDoesNotThrow(() -> albumService.findTracksByAlbumTitle(title));
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
        Optional<AlbumDto> albumById = albumService.findAlbumById(2L);
        albumById.ifPresent(albumDto -> {
            albumDto.setTitle("after update title");
            albumService.updateAlbum(albumDto);
        });
        Optional<AlbumDto> afterUpdate = albumService.findAlbumByTitle("after update title");
        afterUpdate.ifPresent(albumDto -> assertEquals("after update title",
                albumDto.getTitle()));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("update album null test case")
    void updateAlbumNullCase(AlbumDto albumDto) {
        assertDoesNotThrow(() -> albumService.updateAlbum(albumDto));
    }

    @Test
    @DisplayName("delete album default test case")
    void deleteAlbum() {
        Optional<AlbumDto> albumById = albumService.findAlbumById(1L);
        albumById.ifPresent(albumDto -> albumService.deleteAlbum(albumDto.getId()));
        Optional<AlbumDto> albumAfterDelete = albumService.findAlbumById(1L);
        assertThat(albumAfterDelete).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("delete album null case")
    void deleteAlbumNullCase(AlbumDto albumDto) {
        assertDoesNotThrow(() -> albumService.deleteAlbum(albumDto.getId()));
    }
}