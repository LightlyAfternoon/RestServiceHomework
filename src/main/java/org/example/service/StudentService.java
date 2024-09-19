package org.example.service;

import org.example.model.StudentEntity;

import java.sql.SQLException;
import java.util.List;

public interface StudentService {
    StudentEntity findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    StudentEntity save(StudentEntity entity) throws SQLException;

    List<StudentEntity> findAll() throws SQLException;
}
