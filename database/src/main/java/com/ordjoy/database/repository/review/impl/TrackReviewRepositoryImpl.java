package com.ordjoy.database.repository.review.impl;

import com.ordjoy.database.model.review.TrackReview;
import com.ordjoy.database.repository.AbstractGenericCRUDRepository;
import com.ordjoy.database.repository.review.TrackReviewRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrackReviewRepositoryImpl extends AbstractGenericCRUDRepository<TrackReview, Long>
        implements TrackReviewRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public TrackReviewRepositoryImpl(SessionFactory sessionFactory) {
        super(TrackReview.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<TrackReview> findTrackReviewsByTrackTitle(String title) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tr from TrackReview tr join tr.track t where t.title = :trackTitle",
                        TrackReview.class)
                .setParameter("trackTitle", title)
                .getResultList();
    }

    @Override
    public List<TrackReview> findTrackReviewsByTrackId(Long trackId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tr from TrackReview tr join tr.track t where t.id = :trackId",
                        TrackReview.class)
                .setParameter("trackId", trackId)
                .getResultList();
    }

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