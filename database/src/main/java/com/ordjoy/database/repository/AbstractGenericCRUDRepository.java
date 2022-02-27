package com.ordjoy.database.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractGenericCRUDRepository<E, K extends Serializable>
        implements GenericCRUDRepository<E, K> {

    private final Class<E> clazz;
    private final SessionFactory sessionFactory;

    @Override
    public List<E> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<E> criteria = cb.createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria)
                .getResultList();
    }

    @Override
    public Optional<E> findById(K id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.find(clazz, id));
    }

    @Override
    public E add(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.save(entity);
        return entity;
    }

    @Override
    public void update(E newEntityValue) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(newEntityValue);
    }

    @Override
    public void deleteById(K id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(id);
        session.flush();
    }
}