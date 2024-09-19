package org.example.service;

import org.example.model.ExamEntity;

import java.sql.SQLException;
import java.util.List;

public interface ExamService {
    ExamEntity findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    ExamEntity save(ExamEntity entity) throws SQLException;

    List<ExamEntity> findAll() throws SQLException;
}