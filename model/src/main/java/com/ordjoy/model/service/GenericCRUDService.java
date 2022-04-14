package com.ordjoy.model.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * CRUD Service that delegating work to {@link com.ordjoy.model.repository.GenericCRUDRepository}
 *
 * @param <D> DTO that implements {@link Serializable}
 * @param <K> Identifier of DTO
 */
public interface GenericCRUDService<D extends Serializable, K extends Serializable> {

    /**
     * Saves DTO in Service layer
     *
     * @param dto DTO to save
     * @return saved DTO with an Identifier
     */
    D save(D dto);

    /**
     * Finds DTO from the DB by its Identifier
     *
     * @param key DTO Identifier(key)
     * @return {@link Optional} of DTO if present or empty {@link Optional}
     */
    Optional<D> findById(K key);

    /**
     * Finds all DTOs from DB
     *
     * @param limit  LIMIT for pagination
     * @param offset OFFSET for pagination
     * @return List of DTOs
     */
    List<D> list(int limit, int offset);

    /**
     * Updates DTO in Service layer
     *
     * @param dto DTO to update
     */
    void update(D dto);

    /**
     * @return All pages of active DTOs
     * @see com.ordjoy.model.util.PaginationUtils
     */
    Long getAllPages();

    /**
     * Deletes DTO by its key
     *
     * @param key DTO Identifier
     */
    void delete(K key);
}