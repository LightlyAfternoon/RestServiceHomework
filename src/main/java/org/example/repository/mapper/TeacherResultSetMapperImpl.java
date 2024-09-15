package org.example.repository.mapper;

import org.example.model.TeacherEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherResultSetMapperImpl implements TeacherResultSetMapper {
    @Override
    public TeacherEntity map(ResultSet resultSet) throws SQLException {
        try {

            return new TeacherEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("patronymic")
            );
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}