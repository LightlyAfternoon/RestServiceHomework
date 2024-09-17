package org.example.repository.mapper;

import org.example.model.GradeEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface GradeResultSetMapper {
    GradeEntity map(ResultSet resultSet) throws SQLException;
}