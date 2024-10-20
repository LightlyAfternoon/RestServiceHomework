package org.example.repository;

import org.example.model.TeacherEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Set;

@org.springframework.stereotype.Repository
public interface TeacherRepository extends Repository<TeacherEntity, Integer> {
    @Transactional
    TeacherEntity findById(int id) throws SQLException;

    @Modifying
    @Transactional
    void deleteById(int id);

    @Modifying
    @Transactional
    TeacherEntity save(TeacherEntity entity) throws SQLException;

    @Transactional
    Set<TeacherEntity> findAll() throws SQLException;
}