package com.ordjoy.model.repository.album;

import com.ordjoy.model.entity.BaseEntity_;
import com.ordjoy.model.entity.review.AlbumReview;
import com.ordjoy.model.entity.review.AlbumReview_;
import com.ordjoy.model.entity.track.Album;
import com.ordjoy.model.entity.track.Album_;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.util.LoggingUtils;
import com.ordjoy.model.repository.AbstractGenericCRUDRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class AlbumRepositoryImpl extends AbstractGenericCRUDRepository<Album, Long>
        implements AlbumRepository {

    private static final String ALBUM_NAME_PARAM = "albumName";
    private static final String ID_PARAM = "id";

    @Override
    public Optional<Album> findByTitle(String title) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select a from Album a where a.title = :albumName",
                        Album.class)
                .setParameter(ALBUM_NAME_PARAM, title)
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
        List<AlbumReview> albumReviews = session.createQuery(criteria).getResultList();
        log.debug(LoggingUtils.REVIEWS_BY_ALBUM_TITLE_REPO, albumReviews, title);
        return albumReviews;
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
        List<AlbumReview> albumReviews = session.createQuery(criteria).getResultList();
        log.debug(LoggingUtils.REVIEWS_BY_ALBUM_ID_REPO, albumReviews, albumId);
        return albumReviews;
    }

    @Override
    public List<Track> findTracksByAlbumId(Long albumId) {
        Session session = sessionFactory.getCurrentSession();
        List<Track> tracks = session
                .createQuery("select t from Track t join t.album a where a.id = :id",
                        Track.class)
                .setParameter(ID_PARAM, albumId)
                .getResultList();
        log.debug(LoggingUtils.TRACKS_BY_ALBUM_ID_REPO, tracks, albumId);
        return tracks;
    }

    @Override
    public List<Track> findTracksByAlbumTitle(String albumTitle) {
        Session session = sessionFactory.getCurrentSession();
        List<Track> tracks = session
                .createQuery("select t from Track t join t.album a where a.title = :albumName",
                        Track.class)
                .setParameter(ALBUM_NAME_PARAM, albumTitle)
                .getResultList();
        log.debug(LoggingUtils.TRACKS_BY_ALBUM_TITLE_REPO, tracks, albumTitle);
        return tracks;
    }
}