package com.ordjoy.database.repository.review.impl;

import com.ordjoy.database.model.review.AlbumReview;
import com.ordjoy.database.repository.AbstractGenericCRUDRepository;
import com.ordjoy.database.repository.review.AlbumReviewRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlbumReviewRepositoryImpl extends AbstractGenericCRUDRepository<AlbumReview, Long>
        implements AlbumReviewRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public AlbumReviewRepositoryImpl(SessionFactory sessionFactory) {
        super(AlbumReview.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<AlbumReview> findAlbumReviewsByUserLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select ar from AlbumReview ar join ar.user u where u.login = :login",
                        AlbumReview.class)
                .setParameter("login", login)
                .getResultList();
    }

    @Override
    public List<AlbumReview> findAlbumReviewsByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select ar from AlbumReview ar join ar.user u where u.id = :id",
                        AlbumReview.class)
                .setParameter("id", userId)
                .getResultList();
    }
}