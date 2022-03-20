package com.ordjoy.model.repository.track;

import com.ordjoy.model.entity.review.TrackReview;
import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.util.LoggingUtils;
import com.ordjoy.model.repository.AbstractGenericCRUDRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class TrackRepositoryImpl extends AbstractGenericCRUDRepository<Track, Long>
        implements TrackRepository {

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
                .setParameter("title", title)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<TrackReview> findTrackReviewsByTrackTitle(String title) {
        Session session = sessionFactory.getCurrentSession();
        List<TrackReview> trackReviews = session
                .createQuery("select tr from TrackReview tr join tr.track t where t.title = :trackTitle",
                        TrackReview.class)
                .setParameter("trackTitle", title)
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
                .setParameter("trackId", trackId)
                .getResultList();
        log.debug(LoggingUtils.REVIEWS_BY_TRACK_ID_REPO, trackReviews, trackId);
        return trackReviews;
    }
}