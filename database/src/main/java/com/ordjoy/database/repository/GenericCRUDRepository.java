package com.ordjoy.database.repository;

import com.ordjoy.database.model.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericCRUDRepository<E extends BaseEntity<K>, K extends Serializable> {

    E add(E entity);

    Optional<E> findById(K id);

    List<E> findAll();

    void update(E entity);

    void deleteById(K id);
}