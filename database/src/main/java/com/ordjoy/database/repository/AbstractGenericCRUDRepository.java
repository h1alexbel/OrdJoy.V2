package com.ordjoy.database.repository;

import com.ordjoy.database.model.BaseEntity;
import com.ordjoy.database.model.EntityState;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public abstract class AbstractGenericCRUDRepository<E extends BaseEntity<K>, K extends Serializable>
        implements GenericCRUDRepository<E, K> {

    @Autowired
    protected SessionFactory sessionFactory;
    private final Class<E> clazz;

    protected AbstractGenericCRUDRepository() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        clazz = (Class<E>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
    }

    @Override
    public List<E> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<E> criteria = cb.createQuery(clazz);
        Root<E> root = criteria.from(clazz);
        return session.createQuery(criteria.select(root))
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
        entity.setEntityState(EntityState.ACTIVE);
        session.save(entity);
        return entity;
    }

    @Override
    public void update(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(entity);
    }

    @Override
    public void deleteById(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(entity);
    }
}