package com.ordjoy.model.repository.album;

import com.ordjoy.model.entity.BaseEntity_;
import com.ordjoy.model.entity.review.AlbumReview;
import com.ordjoy.model.entity.review.AlbumReview_;
import com.ordjoy.model.entity.track.Album;
import com.ordjoy.model.entity.track.Album_;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.repository.AbstractGenericCRUDRepository;
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
                .where(cb.equal(albumJoin.get(BaseEntity_.id), albumId));
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<Track> findTracksByAlbumId(Long albumId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select t from Track t join t.album a where a.id = :id",
                        Track.class)
                .setParameter("id", albumId)
                .getResultList();
    }

    @Override
    public List<Track> findTracksByAlbumTitle(String albumTitle) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select t from Track t join t.album a where a.title = :albumName",
                        Track.class)
                .setParameter("albumName", albumTitle)
                .getResultList();
    }
}