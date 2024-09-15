package org.example.repository.mapper;

import org.example.model.StudentEntity;
import org.example.model.SubjectEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface StudentResultSetMapper {
    StudentEntity map(ResultSet resultSet) throws SQLException;
}