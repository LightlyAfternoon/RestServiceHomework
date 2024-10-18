package org.example.repository;

import org.example.model.GradeEntity;
import org.springframework.data.repository.Repository;

import java.sql.SQLException;
import java.util.Set;

@org.springframework.stereotype.Repository
public interface GradeRepository extends Repository<GradeEntity, Integer> {
    GradeEntity findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    GradeEntity save(GradeEntity entity) throws SQLException;

    Set<GradeEntity> findAll() throws SQLException;
}