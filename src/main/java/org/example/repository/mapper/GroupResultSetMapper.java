package org.example.repository.mapper;

import org.example.model.GroupEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface GroupResultSetMapper {
    GroupEntity map(ResultSet resultSet) throws SQLException;
}