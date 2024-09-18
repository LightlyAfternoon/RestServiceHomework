package org.example.service;

import org.example.model.ExamEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ExamService {
    ExamEntity findById(int id) throws SQLException, IOException;

    boolean deleteById(int id) throws SQLException, IOException;

    ExamEntity save(ExamEntity entity) throws SQLException, IOException;

    List<ExamEntity> findAll() throws SQLException, IOException;
}