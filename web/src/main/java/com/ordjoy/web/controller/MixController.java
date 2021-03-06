package com.ordjoy.web.controller;

import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.MixReviewDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.service.MixService;
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

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class MixController {

    private final MixService mixService;

    @Autowired
    public MixController(MixService mixService) {
        this.mixService = mixService;
    }

    /**
     * Returns html page with all active mixes
     *
     * @param limit  for UI pagination
     * @param offset for UI pagination
     * @return html page with all active mixes
     * @see Model
     */
    @GetMapping("/auth/mix/all")
    public String getAllMixes(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset, Model model) {
        List<MixDto> mixList = mixService.list(limit, offset);
        Long pages = mixService.getAllPages();
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.MIXES, mixList);
        return PageUtils.MIXES_PAGE;
    }

    /**
     * Returns html page that represents form to add new MixDto
     *
     * @return html page that represents form to add new MixDto
     */
    @GetMapping("/admin/mix/add-mix")
    public String addMixPage() {
        return PageUtils.ADD_MIX_FORM_PAGE;
    }

    /**
     * Saves new MixDto from UI form
     *
     * @param mixDto MixDto from UI form
     * @return redirect to custom mixDto page
     * @see Model
     */
    @PostMapping("/admin/mix/add-mix")
    public String addMix(MixDto mixDto, Model model) {
        if (!mixService.isMixTitleExists(mixDto.getTitle())) {
            MixDto savedMix = mixService.save(mixDto);
            model.addAttribute(AttributeUtils.REQUEST_MIX, savedMix);
            log.debug(LoggingUtils.MIX_WAS_CREATED_IN_CONTROLLER, savedMix);
            return UrlPathUtils.REDIRECT_MIX + savedMix.getId();
        } else {
            return UrlPathUtils.REDIRECT_ADD_MIX_FORM_PAGE;
        }
    }


    /**
     * @param mixId MixDto Identifier
     * @return html page that represents mix with some info
     * @see Model
     */
    @GetMapping("/auth/mix/{id}")
    public String getMix(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long mixId,
            Model model) {
        Optional<MixDto> maybeMix = mixService.findById(mixId);
        if (maybeMix.isPresent()) {
            MixDto mix = maybeMix.get();
            model.addAttribute(AttributeUtils.REQUEST_MIX, mix);
            return PageUtils.MIX_MAIN_PAGE;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param mixTitle MixDto title
     * @return html page that represents mix with some info
     * @see Model
     */
    @GetMapping("/auth/mix")
    public String getMix(
            @RequestParam(value = UrlPathUtils.TITLE_PARAM) String mixTitle,
            Model model) {
        Optional<MixDto> maybeMix = mixService.findMixByTitle(mixTitle);
        if (maybeMix.isPresent()) {
            MixDto mix = maybeMix.get();
            model.addAttribute(AttributeUtils.REQUEST_MIX, mix);
            return PageUtils.MIX_MAIN_PAGE;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Finds all TracksDto with some mixTitle that they have
     * and return html page that represents mix tracks with some info
     *
     * @param mixTitle MixDto title, predicate that must have all tracks
     * @param limit    for UI pagination
     * @param offset   for UI pagination
     * @return html page that represents mix tracks with some info
     * @see Model
     */
    @GetMapping("/auth/mix/tracks")
    public String getMixTracks(
            @RequestParam(value = UrlPathUtils.TITLE_PARAM) String mixTitle,
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset,
            Model model) {
        List<TrackDto> mixTracks = mixService
                .findTracksByMixTitle(mixTitle, limit, offset);
        Long pages = mixService.getTrackWithMixTitlePredicatePages(mixTitle);
        model.addAttribute(AttributeUtils.MIX_TITLE, mixTitle);
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.TRACKS, mixTracks);
        return PageUtils.MIX_TRACKS_PAGE;
    }

    /**
     * Finds all MixReviews with some mixTitle that they have
     * and return html page that represents mix reviews with some info
     *
     * @param mixTitle MixDto title, predicate that must have all mix reviews
     * @param limit    for UI pagination
     * @param offset   for UI pagination
     * @return html page that represents mix reviews with some info
     * @see Model
     */
    @GetMapping("/auth/mix/reviews")
    public String getMixReviews(
            @RequestParam(value = UrlPathUtils.TITLE_PARAM) String mixTitle,
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset,
            Model model) {
        List<MixReviewDto> mixReviews = mixService
                .findMixReviewsByMixTitle(mixTitle, limit, offset);
        Long pages = mixService.getMixReviewWithMixTitlePredicatePages(mixTitle);
        for (MixReviewDto mixReview : mixReviews) {
            model.addAttribute(AttributeUtils.MIX_REVIEW, mixReview);
        }
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.MIX_REVIEWS, mixReviews);
        return PageUtils.CONCRETE_MIX_REVIEWS_PAGE;
    }

    /**
     * Deletes (sets NOT ACTIVE) MixDto
     *
     * @param mixId MixDto Identifier
     * @return html page that represents all active mixes
     */
    @GetMapping("/admin/mix/{id}/remove")
    public String deleteMix(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long mixId) {
        mixService.delete(mixId);
        log.debug(LoggingUtils.MIX_WAS_DELETED_IN_CONTROLLER, mixId);
        return UrlPathUtils.REDIRECT_MIXES_PAGE_WITH_DEFAULT_LIMIT_OFFSET;
    }

    /**
     * Returns html page that represents form to update existing MixDto
     *
     * @param mixId MixDto Identifier
     * @return html page that represents form to update existing MixDto
     * @see Model
     */
    @GetMapping("/admin/mix/update/{id}")
    public String updateMixForm(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long mixId,
            Model model) {
        Optional<MixDto> maybeMix = mixService.findById(mixId);
        maybeMix.ifPresent(mixDto -> model.addAttribute(AttributeUtils.REQUEST_MIX, mixDto));
        return PageUtils.MIX_UPDATE_FORM;
    }

    /**
     * Updates existing Album
     *
     * @param mixDto MixDto from UI form
     * @return redirect to custom MixDto page
     */
    @PostMapping("/admin/mix/update")
    public String updateMix(MixDto mixDto) {
        if (!mixService.isMixTitleExists(mixDto.getTitle())) {
            mixService.update(mixDto);
            log.debug(LoggingUtils.MIX_WAS_UPDATED_IN_CONTROLLER, mixDto);
            return UrlPathUtils.REDIRECT_MIXES_PAGE_WITH_DEFAULT_LIMIT_OFFSET;
        } else {
            return UrlPathUtils.REDIRECT_MIX_UPDATE_FORM + mixDto.getId();
        }
    }
}