package org.example.repository;

import org.example.model.ExamEntity;
import org.springframework.data.repository.Repository;

import java.sql.SQLException;
import java.util.Set;

@org.springframework.stereotype.Repository
public interface ExamRepository extends Repository<ExamEntity, Integer> {
    ExamEntity findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    ExamEntity save(ExamEntity entity) throws SQLException;

    Set<ExamEntity> findAll() throws SQLException;
}