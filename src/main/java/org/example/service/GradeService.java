package org.example.service;

import org.example.model.GradeEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface GradeService {
    GradeEntity findById(int id) throws SQLException, IOException;

    boolean deleteById(int id) throws SQLException, IOException;

    GradeEntity save(GradeEntity entity) throws SQLException, IOException;

    List<GradeEntity> findAll() throws SQLException, IOException;
}