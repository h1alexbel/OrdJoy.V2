package com.ordjoy.model.repository;

import com.ordjoy.model.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @param <E> Persistent object that extends {@link BaseEntity} and is an JPA Entity
 * @param <K> Identifier of Persistent object
 */
public interface GenericCRUDRepository<E extends BaseEntity<K>, K extends Serializable> {

    /**
     * Saves Entity in DB
     *
     * @param entity Entity to save
     * @return saved Persistent Entity with an Identifier
     */
    E add(E entity);

    /**
     * Finds Persistent Object from the DB by its Identifier
     *
     * @param id Entity Identifier
     * @return {@link Optional} of Persistent Object if present or empty {@link Optional}
     */
    Optional<E> findById(K id);

    /**
     * Finds all active entities from DB
     *
     * @param limit  LIMIT of entities
     * @param offset OFFSET of entities
     * @return List of Entities
     */
    List<E> findAll(int limit, int offset);

    /**
     * Updates Entity in DB using session.merge() method
     *
     * @param entity entity to update
     * @see org.hibernate.Session
     */
    void update(E entity);

    /**
     * Deletes Entity from DB (sets NOT_ACTIVE)
     *
     * @param entity to delete
     */
    void delete(E entity);

    /**
     * Gets all Active Table from DB
     *
     * @return Long value of records count
     */
    Long getAllRecords();
}