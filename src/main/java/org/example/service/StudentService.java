package org.example.service;

import org.example.controller.dto.GradeDTO;
import org.example.controller.dto.StudentDTO;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

public interface StudentService {
    StudentDTO findById(int id) throws SQLException;

    void deleteById(int id) throws SQLException;

    StudentDTO save(StudentDTO studentDTO) throws SQLException;

    StudentDTO save(StudentDTO studentDTO, int id) throws SQLException;

    Set<StudentDTO> findAll() throws SQLException;

    Set<GradeDTO> findAllGradesWithServiceId(int id) throws SQLException;
}