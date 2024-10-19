package org.example.repository;

import org.example.model.StudentEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Set;

@org.springframework.stereotype.Repository
public interface StudentRepository extends Repository<StudentEntity, Integer> {
    @Transactional
    StudentEntity findById(int id) throws SQLException;

    @Modifying
    @Transactional
    void deleteById(int id) throws SQLException;

    @Modifying
    @Transactional
    StudentEntity save(StudentEntity entity) throws SQLException;

    @Transactional
    Set<StudentEntity> findAll() throws SQLException;
}