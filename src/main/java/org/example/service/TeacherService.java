package org.example.service;

import org.example.model.TeacherEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface TeacherService {
    TeacherEntity findById(int id) throws SQLException, IOException;

    boolean deleteById(int id) throws SQLException, IOException;

    TeacherEntity save(TeacherEntity entity) throws SQLException, IOException;

    List<TeacherEntity> findAll() throws SQLException, IOException;
}
