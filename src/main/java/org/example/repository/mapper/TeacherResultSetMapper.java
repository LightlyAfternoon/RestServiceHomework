package org.example.repository.mapper;

import org.example.model.TeacherEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface TeacherResultSetMapper {
    TeacherEntity map(ResultSet resultSet) throws SQLException;
}