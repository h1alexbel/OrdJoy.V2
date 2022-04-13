package com.ordjoy.model.repository.impl;

import com.ordjoy.model.entity.review.MixReview;
import com.ordjoy.model.repository.MixReviewRepository;
import com.ordjoy.model.util.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class MixReviewRepositoryImpl extends AbstractGenericCRUDRepository<MixReview, Long>
        implements MixReviewRepository {

    private static final String LOGIN_PARAM = "login";
    private static final String ID_PARAM = "id";

    @Override
    public List<MixReview> findMixReviewsByUserLogin(String login, int limit, int offset) {
        Session session = sessionFactory.getCurrentSession();
        List<MixReview> mixReviews = session
                .createQuery("select mr from MixReview mr join mr.user u" +
                             " where u.login = :login order by mr.id desc",
                        MixReview.class)
                .setParameter(LOGIN_PARAM, login)
                .setMaxResults(limit)
                .setFirstResult(offset)
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
                .setParameter(ID_PARAM, userId)
                .getResultList();
        log.debug(LoggingUtils.REVIEWS_BY_USER_ID_REPO, mixReviews, userId);
        return mixReviews;
    }

    @Override
    public Long getMixReviewWithUserLoginPredicateRecords(String login) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(mr) from MixReview mr" +
                                   " join mr.user u where u.login=:login", Long.class)
                .setParameter(LOGIN_PARAM, login)
                .getSingleResult();
    }
}