package org.example.repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Repository<E> {
    E findById(int id) throws SQLException, IOException;

    boolean deleteById(int id) throws SQLException, IOException;

    E save(E entity) throws SQLException, IOException;

    List<E> findAll() throws SQLException, IOException;
}