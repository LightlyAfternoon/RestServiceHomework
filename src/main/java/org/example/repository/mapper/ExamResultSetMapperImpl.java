package org.example.repository.mapper;

import org.example.model.ExamEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamResultSetMapperImpl implements ExamResultSetMapper {
    @Override
    public ExamEntity map(ResultSet resultSet) throws SQLException {
        try {
            return new ExamEntity(
                    resultSet.getInt("id"),
                    resultSet.getDate("start_date"),
                    resultSet.getInt("group_id"),
                    resultSet.getInt("subject_teacher_id")
            );
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}