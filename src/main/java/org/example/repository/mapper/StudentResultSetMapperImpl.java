package org.example.repository.mapper;

import org.example.model.StudentEntity;
import org.example.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentResultSetMapperImpl implements StudentResultSetMapper {
    @Autowired
    GroupRepository repository;

    @Override
    public StudentEntity map(ResultSet resultSet) throws SQLException {
        try {
            return new StudentEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("patronymic"),
                    repository.findById(resultSet.getInt("group_id"))
            );
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}