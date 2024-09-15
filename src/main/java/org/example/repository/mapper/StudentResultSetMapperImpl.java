package org.example.repository.mapper;

import org.example.model.StudentEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentResultSetMapperImpl implements StudentResultSetMapper {
    @Override
    public StudentEntity map(ResultSet resultSet) throws SQLException {
        try {
            return new StudentEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("patronymic"),
                    resultSet.getInt("group_id")
            );
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}