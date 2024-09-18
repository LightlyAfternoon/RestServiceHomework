package org.example.service;

import org.example.model.StudentEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface StudentService {
    StudentEntity findById(int id) throws SQLException, IOException;

    boolean deleteById(int id) throws SQLException, IOException;

    StudentEntity save(StudentEntity entity) throws SQLException, IOException;

    List<StudentEntity> findAll() throws SQLException, IOException;
}
