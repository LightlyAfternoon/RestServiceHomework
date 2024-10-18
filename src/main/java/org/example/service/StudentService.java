package org.example.service;

import org.example.servlet.dto.StudentDTO;

import java.sql.SQLException;
import java.util.Set;

public interface StudentService {
    StudentDTO findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    StudentDTO save(StudentDTO entity) throws SQLException;

    StudentDTO save(StudentDTO studentDTO, int id) throws SQLException;

    Set<StudentDTO> findAll() throws SQLException;
}