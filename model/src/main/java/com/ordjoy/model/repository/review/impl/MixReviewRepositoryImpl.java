package com.ordjoy.model.repository.review.impl;

import com.ordjoy.model.entity.review.MixReview;
import com.ordjoy.model.util.LoggingUtils;
import com.ordjoy.model.repository.AbstractGenericCRUDRepository;
import com.ordjoy.model.repository.review.MixReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class MixReviewRepositoryImpl extends AbstractGenericCRUDRepository<MixReview, Long>
        implements MixReviewRepository {

    @Override
    public List<MixReview> findMixReviewsByUserLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        List<MixReview> mixReviews = session
                .createQuery("select mr from MixReview mr join mr.user u where u.login = :login",
                        MixReview.class)
                .setParameter("login", login)
                .getResultList();
        log.debug(LoggingUtils.REVIEWS_BY_USER_LOGIN_REPO, mixReviews, login);
        return mixReviews;
    }

    @Override
    public List<MixReview> findMixReviewsByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        List<MixReview> mixReviews = session
                .createQuery("select mr from MixReview mr join mr.user u where u.id = :id",
                        MixReview.class)
                .setParameter("id", userId)
                .getResultList();
        log.debug(LoggingUtils.REVIEWS_BY_USER_ID_REPO, mixReviews, userId);
        return mixReviews;
    }
}