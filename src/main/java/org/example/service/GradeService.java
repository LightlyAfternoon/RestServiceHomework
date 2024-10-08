package org.example.service;

import org.example.model.GradeEntity;
import org.example.servlet.dto.GradeDTO;

import java.sql.SQLException;
import java.util.List;

public interface GradeService {
    GradeDTO findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    GradeDTO save(GradeEntity entity) throws SQLException;

    List<GradeDTO> findAll() throws SQLException;
}