package org.example.service;

import org.example.controller.dto.ExamDTO;

import java.sql.SQLException;
import java.util.Set;

public interface ExamService {
    ExamDTO findById(int id) throws SQLException;

    void deleteById(int id) throws SQLException;

    ExamDTO save(ExamDTO examDTO) throws SQLException;

    ExamDTO save(ExamDTO examDTO, int id) throws SQLException;

    Set<ExamDTO> findAll() throws SQLException;
}