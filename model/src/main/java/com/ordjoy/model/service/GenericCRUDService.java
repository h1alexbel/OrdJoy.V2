package com.ordjoy.model.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericCRUDService<D extends Serializable, K extends Serializable> {

    D save(D dto);

    Optional<D> findById(K key);

    List<D> list(int limit, int offset);

    void update(D dto);

    void delete(K key);
}