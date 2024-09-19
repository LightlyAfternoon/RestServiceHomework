package org.example.repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository<E> {
    E findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    E save(E entity) throws SQLException;

    List<E> findAll() throws SQLException;
}