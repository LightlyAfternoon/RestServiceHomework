package org.example.service;

import org.example.controller.dto.GradeDTO;

import java.sql.SQLException;
import java.util.Set;

public interface GradeService {
    GradeDTO findById(int id) throws SQLException;

    void deleteById(int id) throws SQLException;

    GradeDTO save(GradeDTO gradeDTO) throws SQLException;

    GradeDTO save(GradeDTO gradeDTO, int id) throws SQLException;

    Set<GradeDTO> findAll() throws SQLException;
}