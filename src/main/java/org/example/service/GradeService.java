package org.example.service;

import org.example.model.GradeEntity;

import java.sql.SQLException;
import java.util.List;

public interface GradeService {
    GradeEntity findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    GradeEntity save(GradeEntity entity) throws SQLException;

    List<GradeEntity> findAll() throws SQLException;
}