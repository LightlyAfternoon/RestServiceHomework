package org.example.repository;

import org.example.model.GradeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Set;

@org.springframework.stereotype.Repository
public interface GradeRepository extends Repository<GradeEntity, Integer> {
    @Transactional
    GradeEntity findById(int id) throws SQLException;

    @Transactional
    @Modifying
    void deleteById(int id) throws SQLException;

    @Transactional
    @Modifying
    GradeEntity save(GradeEntity entity) throws SQLException;

    @Transactional
    Set<GradeEntity> findAll() throws SQLException;
}