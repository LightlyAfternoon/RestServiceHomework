package org.example.repository;

import org.example.model.ExamEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Set;

@org.springframework.stereotype.Repository
public interface ExamRepository extends Repository<ExamEntity, Integer> {
    @Transactional
    ExamEntity findById(int id) throws SQLException;

    @Transactional
    @Modifying
    void deleteById(int id) throws SQLException;

    @Transactional
    @Modifying
    ExamEntity save(ExamEntity entity) throws SQLException;

    @Transactional
    Set<ExamEntity> findAll() throws SQLException;
}