package com.ordjoy.model.repository.review.impl;

import com.ordjoy.model.entity.review.TrackReview;
import com.ordjoy.model.repository.AbstractGenericCRUDRepository;
import com.ordjoy.model.repository.review.TrackReviewRepository;
import com.ordjoy.model.util.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class TrackReviewRepositoryImpl extends AbstractGenericCRUDRepository<TrackReview, Long>
        implements TrackReviewRepository {

    private static final String USER_ID_PARAM = "userId";
    private static final String LOGIN_PARAM = "login";

    @Override
    public List<TrackReview> findTrackReviewsByUserLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        List<TrackReview> trackReviews = session
                .createQuery("select tr from TrackReview tr join tr.user u where u.login = :login",
                        TrackReview.class)
                .setParameter(LOGIN_PARAM, login)
                .getResultList();
        log.debug(LoggingUtils.REVIEWS_BY_USER_LOGIN_REPO, trackReviews, login);
        return trackReviews;
    }

    @Override
    public List<TrackReview> findTrackReviewsByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        List<TrackReview> trackReviews = session
                .createQuery("select tr from TrackReview tr join tr.user u where u.id = :userId",
                        TrackReview.class)
                .setParameter(USER_ID_PARAM, userId)
                .getResultList();
        log.debug(LoggingUtils.REVIEWS_BY_USER_ID_REPO, trackReviews, userId);
        return trackReviews;
    }
}