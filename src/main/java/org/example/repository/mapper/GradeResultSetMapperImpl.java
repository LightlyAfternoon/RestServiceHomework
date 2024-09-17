package org.example.repository.mapper;

import org.example.model.GradeEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeResultSetMapperImpl implements GradeResultSetMapper {
    @Override
    public GradeEntity map(ResultSet resultSet) throws SQLException {
        try {
            return new GradeEntity(
                    resultSet.getInt("id"),
                    resultSet.getInt("student_id"),
                    resultSet.getInt("exam_id"),
                    resultSet.getShort("mark")
            );
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}