package com.ordjoy.database.repository.album;

import com.ordjoy.database.model.BaseEntity_;
import com.ordjoy.database.model.review.AlbumReview;
import com.ordjoy.database.model.review.AlbumReview_;
import com.ordjoy.database.model.track.Album;
import com.ordjoy.database.model.track.Album_;
import com.ordjoy.database.repository.AbstractGenericCRUDRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class AlbumRepositoryImpl extends AbstractGenericCRUDRepository<Album, Long>
        implements AlbumRepository {

    @Override
    public Optional<Album> findByTitle(String title) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select a from Album a where a.title = :albumName",
                        Album.class)
                .setParameter("albumName", title)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<AlbumReview> findAlbumReviewsByAlbumTitle(String title) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<AlbumReview> criteria = cb.createQuery(AlbumReview.class);
        Root<AlbumReview> root = criteria.from(AlbumReview.class);
        Join<AlbumReview, Album> albumJoin = root.join(AlbumReview_.album);
        criteria.select(root)
                .where(cb.equal(albumJoin.get(Album_.title), title));
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<AlbumReview> findAlbumReviewsByAlbumId(Long albumId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<AlbumReview> criteria = cb.createQuery(AlbumReview.class);
        Root<AlbumReview> root = criteria.from(AlbumReview.class);
        Join<AlbumReview, Album> albumJoin = root.join(AlbumReview_.album);
        criteria.select(root)
                .where(cb.equal(albumJoin.get(BaseEntity_.id), albumJoin));
        return session.createQuery(criteria).getResultList();
    }
}