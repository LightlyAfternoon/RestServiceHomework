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
    //@EntityGraph(attributePaths = {"subjects", "groups", "exams"})
    TeacherEntity findById(int id) throws SQLException;

    @Modifying
    @Transactional
    void deleteById(int id);

    @Modifying
    @Transactional
    TeacherEntity save(TeacherEntity entity) throws SQLException;

    @Transactional
    //@EntityGraph(attributePaths = {"subjects", "groups", "exams"})
    Set<TeacherEntity> findAll() throws SQLException;
}