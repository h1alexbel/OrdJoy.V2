package com.ordjoy.web.controller;

import com.ordjoy.model.dto.AlbumDto;
import com.ordjoy.model.dto.AlbumReviewDto;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.service.AlbumService;
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
@RequestMapping("/album")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/all")
    public String getAllAlbums(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset, Model model) {
        List<AlbumDto> albums = albumService.list(limit, offset);
        model.addAttribute(AttributeUtils.REQUEST_ALBUMS, albums);
        return PageUtils.ALL_ALBUMS_PAGE;
    }

    @GetMapping("/{id}")
    public String getAlbum(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long id,
                           Model model) {
        Optional<AlbumDto> maybeAlbum = albumService.findById(id);
        if (maybeAlbum.isPresent()) {
            AlbumDto album = maybeAlbum.get();
            model.addAttribute(AttributeUtils.REQUEST_ALBUM, album);
            return PageUtils.ALBUM_MAIN_PAGE;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public String getAlbum(@RequestParam(value = UrlPathUtils.TITLE_PARAM) String title,
                           Model model) {
        Optional<AlbumDto> maybeAlbum = albumService.findAlbumByTitle(title);
        if (maybeAlbum.isPresent()) {
            AlbumDto album = maybeAlbum.get();
            model.addAttribute(AttributeUtils.REQUEST_ALBUM, album);
            return PageUtils.ALBUM_MAIN_PAGE;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/addAlbum")
    public String addAlbumPage() {
        return PageUtils.ADD_ALBUM_PAGE;
    }

    @PostMapping("/addAlbum")
    public String addAlbum(AlbumDto albumDto, Model model) {
        if (!albumService.isAlbumTitleExists(albumDto.getTitle())) {
            AlbumDto savedAlbum = albumService.save(albumDto);
            model.addAttribute(AttributeUtils.REQUEST_ALBUM, savedAlbum);
            log.debug(LoggingUtils.ALBUM_WAS_CREATED_IN_CONTROLLER, savedAlbum);
            return UrlPathUtils.REDIRECT_ALBUM + savedAlbum.getId();
        } else {
            return PageUtils.ADD_ALBUM_PAGE;
        }
    }

    @GetMapping("/{id}/tracks")
    public String getAlbumTracks(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long albumId,
                                 Model model) {
        List<TrackDto> albumTracks = albumService.findTracksByAlbumId(albumId);
        model.addAttribute(AttributeUtils.TRACKS, albumTracks);
        return PageUtils.TRACKS_PAGE;
    }

    @GetMapping("/tracks/")
    public String getAlbumTracks(@RequestParam(value = UrlPathUtils.TITLE_PARAM) String albumTitle,
                                 Model model) {
        List<TrackDto> albumTracks = albumService.findTracksByAlbumTitle(albumTitle);
        model.addAttribute(AttributeUtils.TRACKS, albumTracks);
        return PageUtils.TRACKS_PAGE;
    }

    @GetMapping("/{id}/reviews")
    public String getAlbumReviews(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long albumId,
                                  Model model) {
        List<AlbumReviewDto> albumReviews = albumService.findAlbumReviewsByAlbumId(albumId);
        model.addAttribute(AttributeUtils.ALBUM_REVIEWS, albumReviews);
        return PageUtils.ALBUM_REVIEWS_PAGE;
    }

    @GetMapping("/reviews/")
    public String getAlbumReviews(@RequestParam(value = UrlPathUtils.TITLE_PARAM) String title,
                                  Model model) {
        List<AlbumReviewDto> albumReviews = albumService.findAlbumReviewsByAlbumTitle(title);
        model.addAttribute(AttributeUtils.ALBUM_REVIEWS, albumReviews);
        return PageUtils.ALBUM_REVIEWS_PAGE;
    }

    @GetMapping("/{id}/remove")
    public String deleteAlbum(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long albumId) {
        albumService.deleteAlbum(albumId);
        log.debug(LoggingUtils.ALBUM_WAS_DELETED_IN_CONTROLLER, albumId);
        return UrlPathUtils.REDIRECT_ALL_ALBUMS_WITH_DEFAULT_LIMIT_OFFSET;
    }

    @GetMapping("/update/{id}")
    public String updateAlbumPage(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long id,
            Model model) {
        Optional<AlbumDto> maybeAlbum = albumService.findById(id);
        maybeAlbum.ifPresent(albumDto -> model.addAttribute(AttributeUtils.REQUEST_ALBUM, albumDto));
        return PageUtils.ALBUM_UPDATE_PAGE;
    }

    @PostMapping("/update")
    public String updateAlbum(AlbumDto albumDto) {
        if (!albumService.isAlbumTitleExists(albumDto.getTitle())) {
            albumService.update(albumDto);
            log.debug(LoggingUtils.ALBUM_WAS_UPDATED_IN_CONTROLLER, albumDto);
            return UrlPathUtils.REDIRECT_ALL_ALBUMS_WITH_DEFAULT_LIMIT_OFFSET;
        }
        return PageUtils.ALBUM_UPDATE_FORM;
    }
}