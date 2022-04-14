package com.ordjoy.model.repository.impl;

import com.ordjoy.model.entity.review.TrackReview;
import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.repository.TrackRepository;
import com.ordjoy.model.util.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class TrackRepositoryImpl extends AbstractGenericCRUDRepository<Track, Long>
        implements TrackRepository {

    private static final String TITLE_PARAM = "title";
    private static final String TRACK_ID_PARAM = "trackId";
    private static final String URL_PARAM = "url";

    @Override
    public void addTrackToMix(Track track, Mix mix) {
        mix.addTrack(track);
        log.debug(LoggingUtils.TRACK_WAS_ADDED_TO_MIX_REPO, track, mix);
    }

    @Override
    public Optional<Track> findByTitle(String title) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select t from Track t where t.title = :title",
                        Track.class)
                .setParameter(TITLE_PARAM, title)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Track> findByUrl(String url) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select t from Track t where t.url = :url",
                        Track.class)
                .setParameter(URL_PARAM, url)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<TrackReview> findTrackReviewsByTrackTitle(String title, int limit, int offset) {
        Session session = sessionFactory.getCurrentSession();
        List<TrackReview> trackReviews = session
                .createQuery("select tr from TrackReview tr join tr.track t" +
                             " where t.title = :title order by tr.id desc",
                        TrackReview.class)
                .setParameter(TITLE_PARAM, title)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
        log.debug(LoggingUtils.REVIEWS_BY_TRACK_TITLE_REPO, trackReviews, title);
        return trackReviews;
    }

    @Override
    public List<TrackReview> findTrackReviewsByTrackId(Long trackId) {
        Session session = sessionFactory.getCurrentSession();
        List<TrackReview> trackReviews = session
                .createQuery("select tr from TrackReview tr join tr.track t where t.id = :trackId",
                        TrackReview.class)
                .setParameter(TRACK_ID_PARAM, trackId)
                .getResultList();
        log.debug(LoggingUtils.REVIEWS_BY_TRACK_ID_REPO, trackReviews, trackId);
        return trackReviews;
    }

    @Override
    public Long getTrackReviewWithTrackTitlePredicateRecords(String title) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(tr) from TrackReview tr" +
                                   " join tr.track t where t.title =:title", Long.class)
                .setParameter(TITLE_PARAM, title)
                .getSingleResult();
    }
}