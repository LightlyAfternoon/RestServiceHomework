package org.example.repository.mapper;

import org.example.model.ExamEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ExamResultSetMapper {
    ExamEntity map(ResultSet resultSet) throws SQLException;
}