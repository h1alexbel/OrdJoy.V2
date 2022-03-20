package com.ordjoy.model.repository.mix;

import com.ordjoy.model.entity.BaseEntity_;
import com.ordjoy.model.entity.review.MixReview;
import com.ordjoy.model.entity.review.MixReview_;
import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.entity.track.Mix_;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.log.LoggingUtils;
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
public class MixRepositoryImpl extends AbstractGenericCRUDRepository<Mix, Long>
        implements MixRepository {

    @Override
    public Optional<Mix> findByTitle(String title) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select m from Mix m where m.title = :mixTitle", Mix.class)
                .setParameter("mixTitle", title)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<Track> findTracksByMixTitle(String mixTitle) {
        Session session = sessionFactory.getCurrentSession();
        List<Track> tracks = session
                .createQuery("select t from Mix m join m.tracks t where m.title = :title",
                        Track.class)
                .setParameter("title", mixTitle)
                .getResultList();
        log.debug(LoggingUtils.TRACKS_BY_MIX_TITLE, tracks, mixTitle);
        return tracks;
    }

    @Override
    public List<MixReview> findMixReviewsByMixTitle(String mixName) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<MixReview> criteria = cb.createQuery(MixReview.class);
        Root<MixReview> root = criteria.from(MixReview.class);
        Join<MixReview, Mix> mixJoin = root.join(MixReview_.mix);
        criteria.select(root)
                .where(cb.equal(mixJoin.get(Mix_.title), mixName));
        List<MixReview> mixReviews = session.createQuery(criteria).getResultList();
        log.debug(LoggingUtils.REVIEWS_BY_MIX_TITLE, mixReviews, mixName);
        return mixReviews;
    }

    @Override
    public List<MixReview> findMixReviewsByMixId(Long mixId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<MixReview> criteria = cb.createQuery(MixReview.class);
        Root<MixReview> root = criteria.from(MixReview.class);
        Join<MixReview, Mix> mixJoin = root.join(MixReview_.mix);
        criteria.select(root)
                .where(cb.equal(mixJoin.get(BaseEntity_.id), mixId));
        List<MixReview> mixReviews = session.createQuery(criteria).getResultList();
        log.debug(LoggingUtils.REVIEWS_BY_MIX_ID, mixReviews, mixId);
        return mixReviews;
    }
}