package com.ordjoy.model.service.impl;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.dto.TrackReviewDto;
import com.ordjoy.model.service.AlbumService;
import com.ordjoy.model.service.MixService;
import com.ordjoy.model.service.TrackService;
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
class TrackServiceImplTest {

    @Autowired
    private TrackService trackService;
    @Autowired
    private MixService mixService;
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
    @DisplayName("find all tracks with limit and offset")
    void listTracks() {
        List<TrackDto> tracks = trackService.list(10, 4);
        assertThat(tracks).hasSize(3);
    }

    @Test
    @DisplayName("save track test case")
    void saveTrack() {
        Optional<AlbumDto> maybeAlbum = albumService.findAlbumByTitle("Kanye West - Donda");
        if (maybeAlbum.isPresent()) {
            AlbumDto albumDto = maybeAlbum.get();
            TrackDto saved = trackService.save(TrackDto.builder()
                    .url("https://www.youtube.com/watch?v=grE7x-NeGw8&list=RDgrE7x-NeGw8&start_radio=1")
                    .title("66")
                    .album(albumDto)
                    .build());
            assertThat(saved.getId()).isNotNull();
        }
    }

    @Test
    @DisplayName("add track to mix test case")
    void addTrackToMix() {
        Optional<TrackDto> maybeTrack = trackService.findTrackByTitle("Better now");
        Optional<MixDto> maybeMix = mixService.findMixByTitle("Hip-Hop");
        if (maybeMix.isPresent() && maybeTrack.isPresent()) {
            MixDto mixDto = maybeMix.get();
            TrackDto trackDto = maybeTrack.get();
            trackService.addTrackToMix(trackDto, mixDto);
            List<TrackDto> tracksByMixTitle = mixService.findTracksByMixTitle("Hip-Hop");
            assertThat(tracksByMixTitle).hasSize(1);
        }
    }

    @Test
    @DisplayName("find track by title test case")
    void findTrackByTitle() {
        Optional<TrackDto> maybeTrack = trackService.findTrackByTitle("Unforgiven");
        assertThat(maybeTrack).isNotEmpty();
    }

    @Test
    @DisplayName("is track with this title already exists")
    void isTracksTitleExists() {
        assertThat(trackService.isTracksTitleExists("Unforgiven")).isTrue();
    }

    @Test
    @DisplayName("find track reviews by track title")
    void findTrackReviewsByTrackTitle() {
        List<TrackReviewDto> reviews = trackService.findTrackReviewsByTrackTitle("Paranoid");
        assertThat(reviews).hasSize(1);
    }

    @Test
    @DisplayName("find track reviews by track id")
    void findTrackReviewsByTrackId() {
        Optional<TrackDto> maybeTrack = trackService.findTrackByTitle("Paranoid");
        if (maybeTrack.isPresent()) {
            TrackDto trackDto = maybeTrack.get();
            List<TrackReviewDto> reviews = trackService.findTrackReviewsByTrackId(trackDto.getId());
            assertThat(reviews).hasSize(1);
        }
    }
}