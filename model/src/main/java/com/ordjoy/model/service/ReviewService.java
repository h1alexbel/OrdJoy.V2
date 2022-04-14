package com.ordjoy.model.service;

import com.ordjoy.model.dto.ReviewDto;

import java.io.Serializable;
import java.util.List;

/**
 * @param <D> ReviewDto implementation that extends {@link ReviewDto}
 * @param <K> Identifier of ReviewDto must be {@link Serializable}
 * @see com.ordjoy.model.dto.AlbumReviewDto
 * @see com.ordjoy.model.dto.MixReviewDto
 * @see com.ordjoy.model.dto.TrackReviewDto
 */
public interface ReviewService<D extends ReviewDto, K extends Serializable>
        extends GenericCRUDService<D, K> {

    /**
     * Finds ReviewDtos by User's login
     *
     * @param login  unique User login
     * @param limit  LIMIT for pagination
     * @param offset OFFSET for pagination
     * @return List of ReviewDtos
     */
    List<D> findReviewsByUserLogin(String login, int limit, int offset);

    /**
     * Finds ReviewDtos by User's id
     *
     * @param userId User Identifier
     * @return List of ReviewDtos
     */
    List<D> findReviewsByUserId(Long userId);
}