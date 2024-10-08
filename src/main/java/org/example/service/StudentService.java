package org.example.service;

import org.example.model.StudentEntity;
import org.example.servlet.dto.StudentDTO;

import java.sql.SQLException;
import java.util.List;

public interface StudentService {
    StudentDTO findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    StudentDTO save(StudentEntity entity) throws SQLException;

    List<StudentDTO> findAll() throws SQLException;
}
