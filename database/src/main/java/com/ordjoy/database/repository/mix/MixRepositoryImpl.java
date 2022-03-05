package com.ordjoy.database.repository.mix;

import com.ordjoy.database.model.BaseEntity_;
import com.ordjoy.database.model.review.MixReview;
import com.ordjoy.database.model.review.MixReview_;
import com.ordjoy.database.model.track.Mix;
import com.ordjoy.database.model.track.Mix_;
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
    public List<MixReview> findMixReviewsByMixName(String mixName) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<MixReview> criteria = cb.createQuery(MixReview.class);
        Root<MixReview> root = criteria.from(MixReview.class);
        Join<MixReview, Mix> mixJoin = root.join(MixReview_.mix);
        criteria.select(root)
                .where(cb.equal(mixJoin.get(Mix_.title), mixName));
        return session.createQuery(criteria).getResultList();
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
        return session.createQuery(criteria).getResultList();
    }
}