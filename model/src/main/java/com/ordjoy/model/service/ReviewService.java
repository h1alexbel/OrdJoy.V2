package com.ordjoy.model.service;

import com.ordjoy.model.dto.ReviewDto;

import java.io.Serializable;
import java.util.List;

public interface ReviewService<D extends ReviewDto, K extends Serializable>
        extends GenericCRUDService<D, K> {

    List<D> findReviewsByUserLogin(String login);

    List<D> findReviewsByUserId(Long userId);

    void deleteReview(K key);
}