package org.example.repository;

import java.util.List;

public interface Repository<E> {
    E findById(int id);

    boolean deleteById(int id);

    E save(E entity);

    List<E> findAll();
}