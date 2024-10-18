package org.example.repository;

import org.example.model.StudentEntity;
import org.springframework.data.repository.Repository;

import java.sql.SQLException;
import java.util.Set;

@org.springframework.stereotype.Repository
public interface StudentRepository extends Repository<StudentEntity, Integer> {
    StudentEntity findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    StudentEntity save(StudentEntity entity) throws SQLException;

    Set<StudentEntity> findAll() throws SQLException;
}