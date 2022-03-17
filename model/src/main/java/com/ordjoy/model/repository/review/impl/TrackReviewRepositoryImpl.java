package com.ordjoy.model.repository.review.impl;

import com.ordjoy.model.entity.review.TrackReview;
import com.ordjoy.model.repository.AbstractGenericCRUDRepository;
import com.ordjoy.model.repository.review.TrackReviewRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrackReviewRepositoryImpl extends AbstractGenericCRUDRepository<TrackReview, Long>
        implements TrackReviewRepository {

    @Override
    public List<TrackReview> findTrackReviewsByUserLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tr from TrackReview tr join tr.user u where u.login = :login",
                        TrackReview.class)
                .setParameter("login", login)
                .getResultList();
    }

    @Override
    public List<TrackReview> findTrackReviewsByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tr from TrackReview tr join tr.user u where u.id = :userId",
                        TrackReview.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}