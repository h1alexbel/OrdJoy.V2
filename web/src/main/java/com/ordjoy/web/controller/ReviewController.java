package com.ordjoy.web.controller;

import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.dto.AlbumReviewDto;
import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.MixReviewDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.dto.TrackReviewDto;
import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.service.AlbumReviewService;
import com.ordjoy.model.service.AlbumService;
import com.ordjoy.model.service.MixReviewService;
import com.ordjoy.model.service.MixService;
import com.ordjoy.model.service.TrackReviewService;
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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class ReviewController {

    private final AlbumReviewService albumReviewService;
    private final MixReviewService mixReviewService;
    private final TrackReviewService trackReviewService;
    private final AlbumService albumService;
    private final MixService mixService;
    private final TrackService trackService;

    @Autowired
    public ReviewController(AlbumReviewService albumReviewService,
                            MixReviewService mixReviewService,
                            TrackReviewService trackReviewService,
                            AlbumService albumService, MixService mixService,
                            TrackService trackService) {
        this.albumReviewService = albumReviewService;
        this.mixReviewService = mixReviewService;
        this.trackReviewService = trackReviewService;
        this.albumService = albumService;
        this.mixService = mixService;
        this.trackService = trackService;
    }

    @GetMapping("/user/review/add-album-review")
    public String addAlbumReviewPage(Model model) {
        model.addAttribute(AttributeUtils.ALBUM_REVIEW, AlbumReviewDto.builder().build());
        return PageUtils.ADD_ALBUM_REVIEW_FORM;
    }

    @PostMapping("/user/review/add-album-review")
    public String addAlbumReview(
            @SessionAttribute(AttributeUtils.SESSION_USER) UserDto userDto,
            AlbumReviewDto albumReviewDto, Model model) {
        String albumTitle = albumReviewDto.getAlbum().getTitle();
        Optional<AlbumDto> maybeAlbum = albumService.findAlbumByTitle(albumTitle);
        if (maybeAlbum.isPresent()) {
            AlbumDto albumDto = maybeAlbum.get();
            albumReviewDto.setAlbum(albumDto);
            albumReviewDto.setUser(userDto);
            AlbumReviewDto savedAlbum = albumReviewService.save(albumReviewDto);
            model.addAttribute(AttributeUtils.ALBUM_REVIEW, savedAlbum);
            log.debug(LoggingUtils.REVIEW_WAS_CREATED_IN_CONTROLLER, savedAlbum);
            return UrlPathUtils.REDIRECT_ALBUM_REVIEWS_WITH_DEFAULT_LIMIT_OFFSET;
        } else {
            return UrlPathUtils.REDIRECT_ADD_ALBUM_REVIEW;
        }
    }

    @GetMapping("/user/review/add-mix-review")
    public String addMixReviewPage(Model model) {
        model.addAttribute(AttributeUtils.MIX_REVIEW, MixReviewDto.builder().build());
        return PageUtils.ADD_MIX_REVIEW_FORM;
    }

    @PostMapping("/user/review/add-mix-review")
    public String addMixReview(
            @SessionAttribute(AttributeUtils.SESSION_USER) UserDto userDto,
            MixReviewDto mixReviewDto, Model model) {
        String mixTitle = mixReviewDto.getMix().getTitle();
        Optional<MixDto> maybeMix = mixService.findMixByTitle(mixTitle);
        if (maybeMix.isPresent()) {
            MixDto mixDto = maybeMix.get();
            mixReviewDto.setMix(mixDto);
            mixReviewDto.setUser(userDto);
            MixReviewDto savedMixReview = mixReviewService.save(mixReviewDto);
            model.addAttribute(AttributeUtils.MIX_REVIEW, savedMixReview);
            log.debug(LoggingUtils.REVIEW_WAS_CREATED_IN_CONTROLLER, savedMixReview);
            return UrlPathUtils.REDIRECT_MIX_REVIEWS_WITH_DEFAULT_LIMIT_OFFSET;
        } else {
            return UrlPathUtils.REDIRECT_ADD_MIX_REVIEW;
        }
    }

    @GetMapping("/user/review/add-track-review")
    public String addTrackReviewPage(Model model) {
        model.addAttribute(AttributeUtils.TRACK_REVIEW, TrackReviewDto.builder().build());
        return PageUtils.ADD_TRACK_REVIEW_FORM;
    }

    @PostMapping("/user/review/add-track-review")
    public String addTrackReview(
            @SessionAttribute(AttributeUtils.SESSION_USER) UserDto userDto,
            TrackReviewDto trackReviewDto, Model model) {
        String trackTitle = trackReviewDto.getTrack().getTitle();
        Optional<TrackDto> maybeTrack = trackService.findTrackByTitle(trackTitle);
        if (maybeTrack.isPresent()) {
            TrackDto trackDto = maybeTrack.get();
            trackReviewDto.setTrack(trackDto);
            trackReviewDto.setUser(userDto);
            TrackReviewDto savedTrackReview = trackReviewService.save(trackReviewDto);
            model.addAttribute(AttributeUtils.TRACK_REVIEW, savedTrackReview);
            log.debug(LoggingUtils.REVIEW_WAS_CREATED_IN_CONTROLLER, savedTrackReview);
            return UrlPathUtils.REDIRECT_TRACK_REVIEWS_WITH_DEFAULT_LIMIT_OFFSET;
        } else {
            return UrlPathUtils.REDIRECT_ADD_TRACK_REVIEW;
        }
    }

    @GetMapping("/auth/review/album/{id}")
    public String getAlbumReview(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long id,
            Model model) {
        Optional<AlbumReviewDto> maybeAlbumReview = albumReviewService.findById(id);
        if (maybeAlbumReview.isPresent()) {
            AlbumReviewDto albumReviewDto = maybeAlbumReview.get();
            model.addAttribute(AttributeUtils.ALBUM_REVIEW, albumReviewDto);
            return PageUtils.ALBUM_REVIEW_MAIN;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/auth/review/mix/{id}")
    public String getMixReview(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long id,
            Model model) {
        Optional<MixReviewDto> maybeMixReview = mixReviewService.findById(id);
        if (maybeMixReview.isPresent()) {
            MixReviewDto mixReviewDto = maybeMixReview.get();
            model.addAttribute(AttributeUtils.MIX_REVIEW, mixReviewDto);
            return PageUtils.MIX_REVIEW_MAIN;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/auth/review/track/{id}")
    public String getTrackReview(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long id,
            Model model) {
        Optional<TrackReviewDto> maybeTrackReview = trackReviewService.findById(id);
        if (maybeTrackReview.isPresent()) {
            TrackReviewDto trackReviewDto = maybeTrackReview.get();
            model.addAttribute(AttributeUtils.TRACK_REVIEW, trackReviewDto);
            return PageUtils.TRACK_REVIEW_MAIN;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/auth/review/album/account")
    public String getAlbumReviewsByUserLogin(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset,
            @RequestParam(value = UrlPathUtils.USERNAME_PARAM) String login, Model model) {
        List<AlbumReviewDto> reviewsByUserLogin = albumReviewService
                .findReviewsByUserLogin(login, limit, offset);
        Long pages = albumReviewService.getAlbumReviewWithUserLoginPredicatePages(login);
        for (AlbumReviewDto albumReviewDto : reviewsByUserLogin) {
            model.addAttribute(AttributeUtils.ALBUM_REVIEW, albumReviewDto);
        }
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.ALBUM_REVIEWS, reviewsByUserLogin);
        return PageUtils.USER_ALBUM_REVIEWS_PAGE;
    }

    @GetMapping("/auth/review/mix/account")
    public String getMixReviewsByUserLogin(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset,
            @RequestParam(value = UrlPathUtils.USERNAME_PARAM) String login, Model model) {
        List<MixReviewDto> reviewsByUserLogin = mixReviewService
                .findReviewsByUserLogin(login, limit, offset);
        Long pages = mixReviewService.getMixReviewWithUserLoginPredicatePages(login);
        for (MixReviewDto mixReviewDto : reviewsByUserLogin) {
            model.addAttribute(AttributeUtils.MIX_REVIEW, mixReviewDto);
        }
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.MIX_REVIEWS, reviewsByUserLogin);
        return PageUtils.USER_MIX_REVIEWS_PAGE;
    }

    @GetMapping("/auth/review/track/account")
    public String getTrackReviewsByUserLogin(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset,
            @RequestParam(value = UrlPathUtils.USERNAME_PARAM) String login, Model model) {
        List<TrackReviewDto> reviewsByUserLogin = trackReviewService
                .findReviewsByUserLogin(login, limit, offset);
        Long pages = trackReviewService.getTrackReviewWithUserLoginPredicatePages(login);
        for (TrackReviewDto trackReviewDto : reviewsByUserLogin) {
            model.addAttribute(AttributeUtils.TRACK_REVIEW, trackReviewDto);
        }
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.TRACK_REVIEWS, reviewsByUserLogin);
        return PageUtils.USER_TRACK_REVIEWS_PAGE;
    }

    @GetMapping("/auth/review/album/all")
    public String getAllAlbumReviews(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset, Model model) {
        List<AlbumReviewDto> albumReviews = albumReviewService.list(limit, offset);
        Long pages = albumReviewService.getAllPages();
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.ALBUM_REVIEWS, albumReviews);
        return PageUtils.ALBUM_REVIEWS_PAGE;
    }

    @GetMapping("/auth/review/mix/all")
    public String getAllMixReviews(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset, Model model) {
        List<MixReviewDto> mixReviews = mixReviewService.list(limit, offset);
        Long pages = mixReviewService.getAllPages();
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.MIX_REVIEWS, mixReviews);
        return PageUtils.MIX_REVIEWS_PAGE;
    }

    @GetMapping("/auth/review/track/all")
    public String getAllTrackReviews(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset, Model model) {
        List<TrackReviewDto> trackReviews = trackReviewService.list(limit, offset);
        Long pages = trackReviewService.getAllPages();
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.TRACK_REVIEWS, trackReviews);
        return PageUtils.TRACK_REVIEWS_PAGE;
    }

    @GetMapping("/admin/review/album/{id}/remove")
    public String deleteAlbumReview(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long albumReviewId) {
        albumReviewService.deleteReview(albumReviewId);
        log.debug(LoggingUtils.REVIEW_WAS_DELETED_IN_CONTROLLER, albumReviewId);
        return UrlPathUtils.REDIRECT_ALBUM_REVIEWS_WITH_DEFAULT_LIMIT_OFFSET;
    }

    @GetMapping("/admin/review/mix/{id}/remove")
    public String deleteMixReview(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long mixReviewId) {
        mixReviewService.deleteReview(mixReviewId);
        log.debug(LoggingUtils.REVIEW_WAS_DELETED_IN_CONTROLLER, mixReviewId);
        return UrlPathUtils.REDIRECT_MIX_REVIEWS_WITH_DEFAULT_LIMIT_OFFSET;
    }

    @GetMapping("/admin/review/track/{id}/remove")
    public String deleteTrackReview(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long trackReviewId) {
        trackReviewService.deleteReview(trackReviewId);
        log.debug(LoggingUtils.REVIEW_WAS_DELETED_IN_CONTROLLER, trackReviewId);
        return UrlPathUtils.REDIRECT_TRACK_REVIEWS_WITH_DEFAULT_LIMIT_OFFSET;
    }
}