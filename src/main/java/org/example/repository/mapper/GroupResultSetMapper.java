package org.example.repository.mapper;

import org.example.model.GroupEntity;
import org.example.model.StudentEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface GroupResultSetMapper {
    GroupEntity map(ResultSet resultSet) throws SQLException;
}