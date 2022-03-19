package com.ordjoy.model.repository.review.impl;

import com.ordjoy.model.entity.review.AlbumReview;
import com.ordjoy.model.log.LoggingUtils;
import com.ordjoy.model.repository.AbstractGenericCRUDRepository;
import com.ordjoy.model.repository.review.AlbumReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class AlbumReviewRepositoryImpl extends AbstractGenericCRUDRepository<AlbumReview, Long>
        implements AlbumReviewRepository {

    @Override
    public List<AlbumReview> findAlbumReviewsByUserLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        List<AlbumReview> reviews = session
                .createQuery("select ar from AlbumReview ar join ar.user u where u.login = :login",
                        AlbumReview.class)
                .setParameter("login", login)
                .getResultList();
        log.debug(LoggingUtils.REVIEWS_BY_USER_LOGIN, reviews, login);
        return reviews;
    }

    @Override
    public List<AlbumReview> findAlbumReviewsByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        List<AlbumReview> albumReviews = session
                .createQuery("select ar from AlbumReview ar join ar.user u where u.id = :id",
                        AlbumReview.class)
                .setParameter("id", userId)
                .getResultList();
        log.debug(LoggingUtils.REVIEWS_BY_USER_ID, albumReviews, userId);
        return albumReviews;
    }
}