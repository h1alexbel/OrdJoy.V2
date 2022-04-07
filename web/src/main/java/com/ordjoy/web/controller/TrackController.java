package com.ordjoy.web.controller;

import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.dto.TrackReviewDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/track")
public class TrackController {

    private final TrackService trackService;
    private final MixService mixService;

    @Autowired
    public TrackController(TrackService trackService, MixService mixService) {
        this.trackService = trackService;
        this.mixService = mixService;
    }

    @GetMapping("/auth/all")
    public String getAllTracks(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset, Model model) {
        List<TrackDto> tracks = trackService.list(limit, offset);
        model.addAttribute(AttributeUtils.TRACKS, tracks);
        return PageUtils.TRACKS_PAGE;
    }

    @GetMapping("/admin/addTrack")
    public String addTrackPage() {
        return PageUtils.ADD_TRACK_FORM;
    }

    @PostMapping("/admin/addTrack")
    public String addTrack(TrackDto trackDto, Model model) {
        if (!trackService.isTracksTitleExists(trackDto.getTitle())) {
            TrackDto savedTrack = trackService.save(trackDto);
            model.addAttribute(AttributeUtils.REQUEST_TRACK, savedTrack);
            log.debug(LoggingUtils.TRACK_WAS_CREATED_IN_CONTROLLER, trackDto);
            return UrlPathUtils.REDIRECT_TRACKS_PAGE;
        } else {
            return PageUtils.ADD_TRACK_FORM;
        }
    }

    @GetMapping("/admin/addTrackToMix")
    public String addTrackToMixPage() {
        return PageUtils.ADD_TRACK_TO_MIX_FORM;
    }

    @PostMapping("/admin/addTrackToMix")
    public String addTrackToMix(TrackDto trackDto, MixDto mixDto) {
        Optional<TrackDto> maybeTrack = trackService.findTrackByTitle(trackDto.getTitle());
        Optional<MixDto> maybeMix = mixService.findMixByTitle(mixDto.getTitle());
        if (maybeTrack.isPresent() && maybeMix.isPresent()) {
            TrackDto trackToLink = maybeTrack.get();
            MixDto mixToLink = maybeMix.get();
            trackService.addTrackToMix(trackToLink, mixToLink);
            log.debug(LoggingUtils.TRACK_WAS_ADDED_TO_MIX_IN_CONTROLLER, trackDto, mixDto);
            return UrlPathUtils.REDIRECT_MIXES_PAGE;
        } else {
            return "";
        }
    }

    @GetMapping("/auth/{id}")
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

    @GetMapping("/auth/")
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

    @GetMapping("/auth/{id}/reviews")
    public String getTrackReviews(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long trackId,
                                  Model model) {
        List<TrackReviewDto> trackReviews = trackService.findTrackReviewsByTrackId(trackId);
        model.addAttribute(AttributeUtils.TRACK_REVIEWS, trackReviews);
        return PageUtils.TRACK_REVIEWS_PAGE;
    }

    @GetMapping("/auth/reviews/")
    public String getTrackReviews(@RequestParam(value = UrlPathUtils.TITLE_PARAM) String trackTitle,
                                  Model model) {
        List<TrackReviewDto> trackReviews = trackService.findTrackReviewsByTrackTitle(trackTitle);
        model.addAttribute(AttributeUtils.TRACK_REVIEWS, trackReviews);
        return PageUtils.TRACK_REVIEWS_PAGE;
    }

    @GetMapping("/admin/{id}/remove")
    public String deleteTrack(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long trackId) {
        trackService.deleteTrack(trackId);
        log.debug(LoggingUtils.TRACK_WAS_DELETED_IN_CONTROLLER, trackId);
        return UrlPathUtils.REDIRECT_TRACKS_PAGE_WITH_LIMIT_OFFSET;
    }

    @GetMapping("/admin/update/{id}")
    public String updateTrackForm(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long trackId,
            Model model) {
        Optional<TrackDto> maybeTrack = trackService.findById(trackId);
        maybeTrack.ifPresent(trackDto -> model.addAttribute(AttributeUtils.REQUEST_TRACK, trackDto));
        return PageUtils.TRACK_UPDATE_FORM;
    }

    @PostMapping("/admin/update")
    public String updateTrack(TrackDto trackDto) {
        if (!trackService.isTracksTitleExists(trackDto.getTitle())) {
            trackService.update(trackDto);
            log.debug(LoggingUtils.TRACK_WAS_UPDATED_IN_CONTROLLER, trackDto);
            return UrlPathUtils.REDIRECT_TRACKS_PAGE_WITH_LIMIT_OFFSET;
        } else {
            return PageUtils.TRACK_UPDATE_FORM;
        }
    }
}