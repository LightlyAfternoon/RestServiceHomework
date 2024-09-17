package org.example.repository.mapper;

import org.example.model.GroupEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupResultSetMapperImpl implements GroupResultSetMapper {
    @Override
    public GroupEntity map(ResultSet resultSet) throws SQLException {
        try {
            return new GroupEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("start_date"),
                    resultSet.getDate("end_date"),
                    resultSet.getInt("teacher_id")
            );
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}