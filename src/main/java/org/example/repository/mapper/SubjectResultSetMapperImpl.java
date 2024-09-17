package org.example.repository.mapper;

import org.example.model.SubjectEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectResultSetMapperImpl implements SubjectResultSetMapper {
    @Override
    public SubjectEntity map(ResultSet resultSet) throws SQLException {
        try {
            return new SubjectEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("name")
            );
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}