package com.ordjoy.web.controller;

import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.dto.TrackReviewDto;
import com.ordjoy.model.service.AlbumService;
import com.ordjoy.model.service.MixService;
import com.ordjoy.model.service.TrackService;
import com.ordjoy.model.util.LoggingUtils;
import com.ordjoy.web.util.AttributeUtils;
import com.ordjoy.web.util.PageUtils;
import com.ordjoy.web.util.UrlPathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class TrackController {

    private final AlbumService albumService;
    private final TrackService trackService;
    private final MixService mixService;

    @Autowired
    public TrackController(AlbumService albumService, TrackService trackService, MixService mixService) {
        this.albumService = albumService;
        this.trackService = trackService;
        this.mixService = mixService;
    }

    /**
     * Returns html page with all active tracks
     *
     * @param limit  for UI pagination
     * @param offset for UI pagination
     * @return html page with all active tracks
     * @see Model
     */
    @GetMapping("/auth/track/all")
    public String getAllTracks(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset, Model model) {
        List<TrackDto> tracks = trackService.list(limit, offset);
        Long pages = trackService.getAllPages();
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.TRACKS, tracks);
        return PageUtils.TRACKS_PAGE;
    }

    /**
     * Returns html page that represents form to add new TrackDto
     *
     * @return html page that represents form to add new TrackDto
     */
    @GetMapping("/admin/track/add-track")
    public String addTrackPage(Model model) {
        model.addAttribute(AttributeUtils.REQUEST_TRACK, TrackDto.builder().build());
        return PageUtils.ADD_TRACK_FORM;
    }

    /**
     * Saves new Track from UI form
     *
     * @param trackDto TrackDto from UI form
     * @return redirect to custom track page
     * @see Model
     */
    @PostMapping("/admin/track/add-track")
    public String addTrack(TrackDto trackDto, Model model) {
        String albumTitle = trackDto.getAlbum().getTitle();
        Optional<AlbumDto> maybeAlbum = albumService.findAlbumByTitle(albumTitle);
        if (maybeAlbum.isPresent()) {
            AlbumDto albumDto = maybeAlbum.get();
            trackDto.setAlbum(albumDto);
            if (!trackService.isTracksTitleExists(trackDto.getTitle()) &&
                !trackService.isTracksUrlExists(trackDto.getUrl())) {
                TrackDto savedTrack = trackService.save(trackDto);
                model.addAttribute(AttributeUtils.REQUEST_TRACK, savedTrack);
                log.debug(LoggingUtils.TRACK_WAS_CREATED_IN_CONTROLLER, trackDto);
                return UrlPathUtils.REDIRECT_TRACKS_PAGE;
            } else {
                return UrlPathUtils.REDIRECT_ADD_TRACK_FORM;
            }
        } else {
            return UrlPathUtils.REDIRECT_ADD_TRACK_FORM;
        }
    }

    /**
     * Returns html page that represents form to add existing TrackDto to existing MixDto
     *
     * @return html page that represents form to add existing TrackDto to existing MixDto
     */
    @GetMapping("/admin/track/add-track-to-mix")
    public String addTrackToMixPage() {
        return PageUtils.ADD_TRACK_TO_MIX_FORM;
    }

    /**
     * Adds existing TrackDto to existing MixDto
     *
     * @return redirect to all active mixes pages
     * @see HttpServletRequest
     */
    @PostMapping("/admin/track/add-track-to-mix")
    public String addTrackToMix(HttpServletRequest request) {
        String mixTitle = request.getParameter(AttributeUtils.MIX_TITLE);
        String trackTitle = request.getParameter(AttributeUtils.TRACK_TITLE);
        Optional<TrackDto> maybeTrack = trackService.findTrackByTitle(trackTitle);
        Optional<MixDto> maybeMix = mixService.findMixByTitle(mixTitle);
        if (maybeTrack.isPresent() && maybeMix.isPresent()) {
            TrackDto trackToLink = maybeTrack.get();
            MixDto mixToLink = maybeMix.get();
            trackService.addTrackToMix(trackToLink, mixToLink);
            log.debug(LoggingUtils.TRACK_WAS_ADDED_TO_MIX_IN_CONTROLLER, trackToLink, mixToLink);
            return UrlPathUtils.REDIRECT_MIXES_PAGE;
        } else {
            return UrlPathUtils.REDIRECT_ADD_TRACK_TO_MIX;
        }
    }

    /**
     * @param trackId TrackDto Identifier
     * @return html page that represents track with some info
     * @see Model
     */
    @GetMapping("/auth/track/{id}")
    public String getTrack(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long trackId,
                           Model model) {
        Optional<TrackDto> maybeTrack = trackService.findById(trackId);
        if (maybeTrack.isPresent()) {
            TrackDto track = maybeTrack.get();
            model.addAttribute(AttributeUtils.REQUEST_TRACK, track);
            return PageUtils.TRACK_MAIN_PAGE;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param trackTitle TrackDto title
     * @return html page that represents track with some info
     * @see Model
     */
    @GetMapping("/auth/track")
    public String getTrack(@RequestParam(value = UrlPathUtils.TITLE_PARAM) String trackTitle,
                           Model model) {
        Optional<TrackDto> maybeTrack = trackService.findTrackByTitle(trackTitle);
        if (maybeTrack.isPresent()) {
            TrackDto track = maybeTrack.get();
            model.addAttribute(AttributeUtils.REQUEST_TRACK, track);
            return PageUtils.TRACK_MAIN_PAGE;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Finds all TrackReviewDtos with some trackTitle that they have
     * and return html page that represents album reviews with some info
     *
     * @param trackTitle TrackDto title, predicate that must have all reviews
     * @param limit      for UI pagination
     * @param offset     for UI pagination
     * @return html page that represents track reviews with some info
     * @see Model
     */
    @GetMapping("/auth/track/reviews")
    public String getTrackReviews(
            @RequestParam(value = UrlPathUtils.TITLE_PARAM) String trackTitle,
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset,
            Model model) {
        List<TrackReviewDto> trackReviews = trackService
                .findTrackReviewsByTrackTitle(trackTitle, limit, offset);
        Long pages = trackService.getTrackReviewWithTrackTitlePredicatePages(trackTitle);
        for (TrackReviewDto trackReview : trackReviews) {
            model.addAttribute(AttributeUtils.TRACK_REVIEW, trackReview);
        }
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.TRACK_REVIEWS, trackReviews);
        return PageUtils.CONCRETE_TRACK_REVIEWS_PAGE;
    }

    /**
     * Deletes (sets NOT ACTIVE) TrackDto
     *
     * @param trackId TrackDto Identifier
     * @return html page that represents all active tracks
     */
    @GetMapping("/admin/track/{id}/remove")
    public String deleteTrack(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long trackId) {
        trackService.delete(trackId);
        log.debug(LoggingUtils.TRACK_WAS_DELETED_IN_CONTROLLER, trackId);
        return UrlPathUtils.REDIRECT_TRACKS_PAGE_WITH_LIMIT_OFFSET;
    }

    /**
     * Returns html page that represents form to update existing TrackDto
     *
     * @param trackId TrackDto Identifier
     * @return html page that represents form to update existing TrackDto
     * @see Model
     */
    @GetMapping("/admin/track/update/{id}")
    public String updateTrackForm(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long trackId,
            Model model) {
        Optional<TrackDto> maybeTrack = trackService.findById(trackId);
        maybeTrack.ifPresent(trackDto -> model.addAttribute(AttributeUtils.REQUEST_TRACK, trackDto));
        return PageUtils.TRACK_UPDATE_FORM;
    }

    /**
     * Updates existing Track
     *
     * @param trackDto TrackDto from UI form
     * @return redirect to custom TrackDto page
     */
    @PostMapping("/admin/track/update")
    public String updateTrack(TrackDto trackDto) {
        if (!trackService.isTracksTitleExists(trackDto.getTitle())) {
            trackService.update(trackDto);
            log.debug(LoggingUtils.TRACK_WAS_UPDATED_IN_CONTROLLER, trackDto);
            return UrlPathUtils.REDIRECT_TRACKS_PAGE_WITH_LIMIT_OFFSET;
        } else {
            return UrlPathUtils.REDIRECT_TRACK_UPDATE_FORM + trackDto.getId();
        }
    }
}