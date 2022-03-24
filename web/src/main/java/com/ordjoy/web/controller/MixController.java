package com.ordjoy.web.controller;

import com.ordjoy.model.dto.MixDto;
import com.ordjoy.model.dto.MixReviewDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.service.mix.MixService;
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
@RequestMapping("/mix")
public class MixController {

    private final MixService mixService;

    @Autowired
    public MixController(MixService mixService) {
        this.mixService = mixService;
    }

    @GetMapping("/all")
    public String getAllMixes(Model model) {
        List<MixDto> mixList = mixService.listMixes();
        model.addAttribute(AttributeUtils.MIXES, mixList);
        return PageUtils.MIXES_PAGE;
    }

    @GetMapping("/addMix")
    public String addMixPage() {
        return PageUtils.ADD_MIX_FORM_PAGE;
    }

    @PostMapping("/addMix")
    public String addMix(MixDto mixDto, Model model) {
        if (!mixService.isMixTitleExists(mixDto.getTitle())) {
            MixDto savedMix = mixService.saveMix(mixDto);
            model.addAttribute(AttributeUtils.REQUEST_MIX, savedMix);
            log.debug(LoggingUtils.MIX_WAS_CREATED_IN_CONTROLLER, savedMix);
            return UrlPathUtils.REDIRECT_MIXES_PAGE;
        } else {
            return PageUtils.ADD_MIX_FORM_PAGE;
        }
    }

    @GetMapping("/{id}")
    public String getMix(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long mixId,
                         Model model) {
        Optional<MixDto> maybeMix = mixService.findMixById(mixId);
        if (maybeMix.isPresent()) {
            MixDto mix = maybeMix.get();
            model.addAttribute(AttributeUtils.REQUEST_MIX, mix);
            return PageUtils.MIX_MAIN_PAGE;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public String getMix(@RequestParam(value = UrlPathUtils.TITLE_PARAM) String mixTitle,
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

    @GetMapping("/tracks/")
    public String getMixTracks(@RequestParam(value = UrlPathUtils.TITLE_PARAM) String mixTitle,
                               Model model) {
        List<TrackDto> mixTracks = mixService.findTracksByMixTitle(mixTitle);
        model.addAttribute(AttributeUtils.MIX_TRACKS, mixTracks);
        return PageUtils.MIX_TRACKS_PAGE;
    }

    @GetMapping("/{id}/reviews")
    public String getMixReviews(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long mixId,
                                Model model) {
        List<MixReviewDto> mixReviews = mixService.findMixReviewsByMixId(mixId);
        model.addAttribute(AttributeUtils.MIX_REVIEWS, mixReviews);
        return PageUtils.MIX_REVIEWS_PAGE;
    }

    @GetMapping("/reviews/")
    public String getMixReviews(@RequestParam(value = UrlPathUtils.TITLE_PARAM) String mixTitle,
                                Model model) {
        List<MixReviewDto> mixReviews = mixService.findMixReviewsByMixTitle(mixTitle);
        model.addAttribute(AttributeUtils.MIX_REVIEWS, mixReviews);
        return PageUtils.MIX_REVIEWS_PAGE;
    }

    @PostMapping("/{id}/remove")
    public String deleteMix(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long mixId) {
        mixService.deleteMix(mixId);
        log.debug(LoggingUtils.MIX_WAS_DELETED_IN_CONTROLLER, mixId);
        return UrlPathUtils.REDIRECT_MIXES_PAGE;
    }

    @PostMapping("/update")
    public String updateMix(MixDto mixDto) {
        if (!mixService.isMixTitleExists(mixDto.getTitle())) {
            mixService.updateMix(mixDto);
            log.debug(LoggingUtils.MIX_WAS_UPDATED_IN_CONTROLLER, mixDto);
            return UrlPathUtils.REDIRECT_MIXES_PAGE;
        } else {
            return PageUtils.MIX_UPDATE_FORM;
        }
    }
}