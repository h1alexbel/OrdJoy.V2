package com.ordjoy.database.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericCRUDRepository<E, K extends Serializable> {

    E add(E entity);

    Optional<E> findById(K id);

    List<E> findAll();

    void update(E newEntityValue);

    void deleteById(K id);
}