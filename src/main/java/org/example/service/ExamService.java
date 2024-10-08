package org.example.service;

import org.example.model.ExamEntity;
import org.example.servlet.dto.ExamDTO;

import java.sql.SQLException;
import java.util.List;

public interface ExamService {
    ExamDTO findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    ExamDTO save(ExamEntity entity) throws SQLException;

    List<ExamDTO> findAll() throws SQLException;
}