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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    /**
     * Returns html page with all active albums
     *
     * @param limit  for UI pagination
     * @param offset for UI pagination
     * @return html page with all active albums
     * @see Model
     */
    @GetMapping("/auth/album/all")
    public String getAllAlbums(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset, Model model) {
        List<AlbumDto> albums = albumService.list(limit, offset);
        Long pages = albumService.getAllPages();
        model.addAttribute(AttributeUtils.REQUEST_ALBUMS, albums);
        model.addAttribute(AttributeUtils.PAGES, pages);
        return PageUtils.ALL_ALBUMS_PAGE;
    }

    /**
     * @param id AlbumDto Identifier
     * @return html page that represents album with some info
     * @see Model
     */
    @GetMapping("/auth/album/{id}")
    public String getAlbum(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long id,
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

    /**
     * @param title AlbumDto title
     * @return html page that represents album with some info
     * @see Model
     */
    @GetMapping("/auth/album")
    public String getAlbum(
            @RequestParam(value = UrlPathUtils.TITLE_PARAM) String title,
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

    /**
     * Returns html page that represents form to add new AlbumDto
     *
     * @return html page that represents form to add new AlbumDto
     */
    @GetMapping("/admin/album/add-album")
    public String addAlbumPage() {
        return PageUtils.ADD_ALBUM_PAGE;
    }

    /**
     * Saves new Album from UI form
     *
     * @param albumDto AlbumDto from UI form
     * @return redirect to custom album page
     * @see Model
     */
    @PostMapping("/admin/album/add-album")
    public String addAlbum(AlbumDto albumDto, Model model) {
        if (!albumService.isAlbumTitleExists(albumDto.getTitle())) {
            AlbumDto savedAlbum = albumService.save(albumDto);
            model.addAttribute(AttributeUtils.REQUEST_ALBUM, savedAlbum);
            log.debug(LoggingUtils.ALBUM_WAS_CREATED_IN_CONTROLLER, savedAlbum);
            return UrlPathUtils.REDIRECT_ALBUM + savedAlbum.getId();
        } else {
            return UrlPathUtils.REDIRECT_ADD_ALBUM_PAGE;
        }
    }

    /**
     * Finds all TracksDto with some albumTitle that they have
     * and return html page that represents album tracks with some info
     *
     * @param albumTitle AlbumDto title, predicate that must have all tracks
     * @param limit      for UI pagination
     * @param offset     for UI pagination
     * @return html page that represents album tracks with some info
     * @see Model
     */
    @GetMapping("/auth/album/tracks")
    public String getAlbumTracks(
            @RequestParam(value = UrlPathUtils.TITLE_PARAM) String albumTitle,
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset,
            Model model) {
        List<TrackDto> albumTracks = albumService
                .findTracksByAlbumTitle(albumTitle, limit, offset);
        Long pages = albumService.getTrackWithAlbumTitlePredicatePages(albumTitle);
        for (TrackDto albumTrack : albumTracks) {
            model.addAttribute(AttributeUtils.REQUEST_TRACK, albumTrack);
        }
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.TRACKS, albumTracks);
        return PageUtils.ALBUM_TRACKS_PAGE;
    }

    /**
     * Finds all AlbumReviewDtos with some albumTitle that they have
     * and return html page that represents album reviews with some info
     *
     * @param title  AlbumDto title, predicate that must have all reviews
     * @param limit  for UI pagination
     * @param offset for UI pagination
     * @return html page that represents album reviews with some info
     * @see Model
     */
    @GetMapping("/auth/album/reviews")
    public String getAlbumReviews(
            @RequestParam(value = UrlPathUtils.TITLE_PARAM) String title,
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset,
            Model model) {
        List<AlbumReviewDto> albumReviews = albumService
                .findAlbumReviewsByAlbumTitle(title, limit, offset);
        Long pages = albumService.getAlbumReviewWithAlbumTitlePredicatePages(title);
        for (AlbumReviewDto albumReview : albumReviews) {
            model.addAttribute(AttributeUtils.ALBUM_REVIEW, albumReview);
        }
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.ALBUM_REVIEWS, albumReviews);
        return PageUtils.CONCRETE_ALBUM_REVIEWS_PAGE;
    }

    /**
     * Deletes (sets NOT ACTIVE) AlbumDto
     *
     * @param albumId AlbumDto Identifier
     * @return html page that represents all active albums
     */
    @GetMapping("/admin/album/{id}/remove")
    public String deleteAlbum(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long albumId) {
        albumService.delete(albumId);
        log.debug(LoggingUtils.ALBUM_WAS_DELETED_IN_CONTROLLER, albumId);
        return UrlPathUtils.REDIRECT_ALL_ALBUMS_WITH_DEFAULT_LIMIT_OFFSET;
    }

    /**
     * Returns html page that represents form to update existing AlbumDto
     *
     * @param id AlbumDto Identifier
     * @return html page that represents form to update existing AlbumDto
     * @see Model
     */
    @GetMapping("/admin/album/update/{id}")
    public String updateAlbumPage(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long id,
            Model model) {
        Optional<AlbumDto> maybeAlbum = albumService.findById(id);
        maybeAlbum.ifPresent(albumDto -> model.addAttribute(AttributeUtils.REQUEST_ALBUM, albumDto));
        return PageUtils.ALBUM_UPDATE_PAGE;
    }

    /**
     * Updates existing Album
     *
     * @param albumDto AlbumDto from UI form
     * @return redirect to custom AlbumDto page
     */
    @PostMapping("/admin/album/update")
    public String updateAlbum(AlbumDto albumDto) {
        if (!albumService.isAlbumTitleExists(albumDto.getTitle())) {
            albumService.update(albumDto);
            log.debug(LoggingUtils.ALBUM_WAS_UPDATED_IN_CONTROLLER, albumDto);
            return UrlPathUtils.REDIRECT_ALL_ALBUMS_WITH_DEFAULT_LIMIT_OFFSET;
        } else {
            return UrlPathUtils.REDIRECT_ALBUM_UPDATE_FORM + albumDto.getId();
        }
    }
}