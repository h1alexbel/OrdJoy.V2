package com.ordjoy.model.repository.review.impl;

import com.ordjoy.model.entity.review.MixReview;
import com.ordjoy.model.repository.AbstractGenericCRUDRepository;
import com.ordjoy.model.repository.review.MixReviewRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MixReviewRepositoryImpl extends AbstractGenericCRUDRepository<MixReview, Long>
        implements MixReviewRepository {

    @Override
    public List<MixReview> findMixReviewsByUserLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select mr from MixReview mr join mr.user u where u.login = :login",
                        MixReview.class)
                .setParameter("login", login)
                .getResultList();
    }

    @Override
    public List<MixReview> findMixReviewsByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select mr from MixReview mr join mr.user u where u.id = :id",
                        MixReview.class)
                .setParameter("id", userId)
                .getResultList();
    }
}