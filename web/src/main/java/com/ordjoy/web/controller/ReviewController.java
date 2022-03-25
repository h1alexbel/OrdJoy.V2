package com.ordjoy.web.controller;

import com.ordjoy.model.dto.AlbumReviewDto;
import com.ordjoy.model.dto.MixReviewDto;
import com.ordjoy.model.dto.TrackReviewDto;
import com.ordjoy.model.service.review.AlbumReviewService;
import com.ordjoy.model.service.review.MixReviewService;
import com.ordjoy.model.service.review.TrackReviewService;
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
@RequestMapping("/review")
public class ReviewController {

    private final AlbumReviewService albumReviewService;
    private final MixReviewService mixReviewService;
    private final TrackReviewService trackReviewService;

    @Autowired
    public ReviewController(AlbumReviewService albumReviewService,
                            MixReviewService mixReviewService,
                            TrackReviewService trackReviewService) {
        this.albumReviewService = albumReviewService;
        this.mixReviewService = mixReviewService;
        this.trackReviewService = trackReviewService;
    }

    @GetMapping("/addAlbumReview")
    public String addAlbumReviewPage() {
        return PageUtils.ADD_ALBUM_REVIEW_FORM;
    }

    @PostMapping("/addAlbumReview")
    public String addAlbumReview(AlbumReviewDto albumReviewDto, Model model) {
        AlbumReviewDto savedAlbum = albumReviewService.saveReview(albumReviewDto);
        model.addAttribute(AttributeUtils.ALBUM_REVIEW, savedAlbum);
        log.debug(LoggingUtils.REVIEW_WAS_CREATED_IN_CONTROLLER, savedAlbum);
        return UrlPathUtils.REDIRECT_ALBUM_REVIEW_MAIN;
    }

    @GetMapping("/addMixReview")
    public String addMixReviewPage() {
        return PageUtils.ADD_MIX_REVIEW_FORM;
    }

    @PostMapping("/addMixReview")
    public String addMixReview(MixReviewDto mixReviewDto, Model model) {
        MixReviewDto savedMixReview = mixReviewService.saveReview(mixReviewDto);
        model.addAttribute(AttributeUtils.MIX_REVIEW, savedMixReview);
        log.debug(LoggingUtils.REVIEW_WAS_CREATED_IN_CONTROLLER, savedMixReview);
        return UrlPathUtils.REDIRECT_MIX_REVIEW_MAIN;
    }

    @GetMapping("/addTrackReview")
    public String addTrackReviewPage() {
        return PageUtils.ADD_TRACK_REVIEW_FORM;
    }

    @PostMapping("/addTrackReview")
    public String addTrackReview(TrackReviewDto trackReviewDto, Model model) {
        TrackReviewDto savedTrackReview = trackReviewService.saveReview(trackReviewDto);
        model.addAttribute(AttributeUtils.TRACK_REVIEW, savedTrackReview);
        log.debug(LoggingUtils.REVIEW_WAS_CREATED_IN_CONTROLLER, savedTrackReview);
        return UrlPathUtils.REDIRECT_TRACK_REVIEW_MAIN;
    }

    @GetMapping("/album/{id}")
    public String getAlbumReview(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long id,
                                 Model model) {
        Optional<AlbumReviewDto> maybeAlbumReview = albumReviewService.findReviewById(id);
        if (maybeAlbumReview.isPresent()) {
            AlbumReviewDto albumReviewDto = maybeAlbumReview.get();
            model.addAttribute(AttributeUtils.ALBUM_REVIEW, albumReviewDto);
            return PageUtils.ALBUM_REVIEW_MAIN;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/mix/{id}")
    public String getMixReview(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long id,
                               Model model) {
        Optional<MixReviewDto> maybeMixReview = mixReviewService.findReviewById(id);
        if (maybeMixReview.isPresent()) {
            MixReviewDto mixReviewDto = maybeMixReview.get();
            model.addAttribute(AttributeUtils.MIX_REVIEW, mixReviewDto);
            return PageUtils.MIX_REVIEW_MAIN;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/track/{id}")
    public String getTrackReview(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long id,
                                 Model model) {
        Optional<TrackReviewDto> maybeTrackReview = trackReviewService.findReviewById(id);
        if (maybeTrackReview.isPresent()) {
            TrackReviewDto trackReviewDto = maybeTrackReview.get();
            model.addAttribute(AttributeUtils.TRACK_REVIEW, trackReviewDto);
            return PageUtils.TRACK_REVIEW_MAIN;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("album/user/")
    public String getAlbumReviewsByUserLogin(
            @RequestParam(value = UrlPathUtils.USERNAME_PARAM) String login, Model model) {
        List<AlbumReviewDto> reviewsByUserLogin = albumReviewService.findReviewsByUserLogin(login);
        model.addAttribute(AttributeUtils.ALBUM_REVIEWS, reviewsByUserLogin);
        return PageUtils.ALBUM_REVIEWS_PAGE;
    }

    @GetMapping("album/user/{id}")
    public String getAlbumReviewsByUserId(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long userId, Model model) {
        List<AlbumReviewDto> reviewsByUserId = albumReviewService.findReviewsByUserId(userId);
        model.addAttribute(AttributeUtils.ALBUM_REVIEWS, reviewsByUserId);
        return PageUtils.ALBUM_REVIEWS_PAGE;
    }

    @GetMapping("mix/user/")
    public String getMixReviewsByUserLogin(
            @RequestParam(value = UrlPathUtils.USERNAME_PARAM) String login, Model model) {
        List<MixReviewDto> reviewsByUserLogin = mixReviewService.findReviewsByUserLogin(login);
        model.addAttribute(AttributeUtils.MIX_REVIEWS, reviewsByUserLogin);
        return PageUtils.MIX_REVIEWS_PAGE;
    }

    @GetMapping("mix/user/{id}")
    public String getMixReviewsByUserId(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long userId, Model model) {
        List<MixReviewDto> reviewsByUserId = mixReviewService.findReviewsByUserId(userId);
        model.addAttribute(AttributeUtils.MIX_REVIEWS, reviewsByUserId);
        return PageUtils.MIX_REVIEWS_PAGE;
    }

    @GetMapping("track/user/")
    public String getTrackReviewsByUserLogin(
            @RequestParam(value = UrlPathUtils.USERNAME_PARAM) String login, Model model) {
        List<TrackReviewDto> reviewsByUserLogin = trackReviewService.findReviewsByUserLogin(login);
        model.addAttribute(AttributeUtils.TRACK_REVIEWS, reviewsByUserLogin);
        return PageUtils.TRACK_REVIEWS_PAGE;
    }

    @GetMapping("track/user/{id}")
    public String getTrackReviewsByUserId(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long userId, Model model) {
        List<TrackReviewDto> reviewsByUserId = trackReviewService.findReviewsByUserId(userId);
        model.addAttribute(AttributeUtils.TRACK_REVIEWS, reviewsByUserId);
        return PageUtils.TRACK_REVIEWS_PAGE;
    }

    @GetMapping("/album/all/")
    public String getAllAlbumReviews(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset, Model model) {
        List<AlbumReviewDto> albumReviews = albumReviewService.listReviews(limit, offset);
        model.addAttribute(AttributeUtils.ALBUM_REVIEWS, albumReviews);
        return PageUtils.ALBUM_REVIEWS_PAGE;
    }

    @GetMapping("/mix/all/")
    public String getAllMixReviews(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset, Model model) {
        List<MixReviewDto> mixReviews = mixReviewService.listReviews(limit, offset);
        model.addAttribute(AttributeUtils.MIX_REVIEWS, mixReviews);
        return PageUtils.MIX_REVIEWS_PAGE;
    }

    @GetMapping("/track/all/")
    public String getAllTrackReviews(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset, Model model) {
        List<TrackReviewDto> trackReviews = trackReviewService.listReviews(limit, offset);
        model.addAttribute(AttributeUtils.TRACK_REVIEWS, trackReviews);
        return PageUtils.TRACK_REVIEWS_PAGE;
    }

    @PostMapping("/album/update")
    public String updateAlbumReview(AlbumReviewDto albumReviewDto) {
        albumReviewService.updateReview(albumReviewDto);
        log.debug(LoggingUtils.REVIEW_WAS_UPDATED_IN_CONTROLLER, albumReviewDto);
        return UrlPathUtils.REDIRECT_ALBUM_REVIEWS;
    }

    @PostMapping("/mix/update")
    public String updateMixReview(MixReviewDto mixReviewDto) {
        mixReviewService.updateReview(mixReviewDto);
        log.debug(LoggingUtils.REVIEW_WAS_UPDATED_IN_CONTROLLER, mixReviewDto);
        return UrlPathUtils.REDIRECT_MIX_REVIEWS;
    }

    @PostMapping("/track/update")
    public String updateTrackReview(TrackReviewDto trackReviewDto) {
        trackReviewService.updateReview(trackReviewDto);
        log.debug(LoggingUtils.REVIEW_WAS_UPDATED_IN_CONTROLLER, trackReviewDto);
        return UrlPathUtils.REDIRECT_TRACK_REVIEWS;
    }

    @PostMapping("/album/{id}/remove")
    public String deleteAlbumReview(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long albumReviewId) {
        albumReviewService.deleteReview(albumReviewId);
        log.debug(LoggingUtils.REVIEW_WAS_DELETED_IN_CONTROLLER, albumReviewId);
        return UrlPathUtils.REDIRECT_ALBUM_REVIEWS;
    }

    @PostMapping("/mix/{id}/remove")
    public String deleteMixReview(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long mixReviewId) {
        mixReviewService.deleteReview(mixReviewId);
        log.debug(LoggingUtils.REVIEW_WAS_DELETED_IN_CONTROLLER, mixReviewId);
        return UrlPathUtils.REDIRECT_MIX_REVIEWS;
    }

    @PostMapping("/track/{id}/remove")
    public String deleteTrackReview(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long trackReviewId) {
        trackReviewService.deleteReview(trackReviewId);
        log.debug(LoggingUtils.REVIEW_WAS_DELETED_IN_CONTROLLER, trackReviewId);
        return UrlPathUtils.REDIRECT_TRACK_REVIEWS;
    }
}