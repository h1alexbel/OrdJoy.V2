package com.ordjoy.model.repository.impl;

import com.ordjoy.model.entity.review.AlbumReview;
import com.ordjoy.model.repository.AlbumReviewRepository;
import com.ordjoy.model.util.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class AlbumReviewRepositoryImpl extends AbstractGenericCRUDRepository<AlbumReview, Long>
        implements AlbumReviewRepository {

    private static final String LOGIN_PARAM = "login";
    private static final String ID_PARAM = "id";

    @Override
    public List<AlbumReview> findAlbumReviewsByUserLogin(String login, int limit, int offset) {
        Session session = sessionFactory.getCurrentSession();
        List<AlbumReview> reviews = session
                .createQuery("select ar from AlbumReview ar join ar.user u " +
                             "where u.login = :login order by ar.id desc",
                        AlbumReview.class)
                .setParameter(LOGIN_PARAM, login)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
        log.debug(LoggingUtils.REVIEWS_BY_USER_LOGIN_REPO, reviews, login);
        return reviews;
    }

    @Override
    public List<AlbumReview> findAlbumReviewsByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        List<AlbumReview> albumReviews = session
                .createQuery("select ar from AlbumReview ar join ar.user u where u.id = :id",
                        AlbumReview.class)
                .setParameter(ID_PARAM, userId)
                .getResultList();
        log.debug(LoggingUtils.REVIEWS_BY_USER_ID_REPO, albumReviews, userId);
        return albumReviews;
    }

    @Override
    public Long getAlbumReviewWithUserLoginPredicateRecords(String login) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(ar) from AlbumReview ar" +
                                   " join ar.user u where u.login =:login", Long.class)
                .setParameter(LOGIN_PARAM, login)
                .getSingleResult();
    }
}