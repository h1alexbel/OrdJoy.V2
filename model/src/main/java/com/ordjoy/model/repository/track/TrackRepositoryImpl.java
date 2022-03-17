package com.ordjoy.model.repository.track;

import com.ordjoy.model.entity.review.TrackReview;
import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.repository.AbstractGenericCRUDRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrackRepositoryImpl extends AbstractGenericCRUDRepository<Track, Long>
        implements TrackRepository {

    @Override
    public void addTrackToMix(Track track, Mix mix) {
        mix.addTrack(track);
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
}