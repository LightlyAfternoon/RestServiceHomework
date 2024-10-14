package org.example.repository.mapper;

import org.example.model.GroupEntity;
import org.example.repository.StudentRepository;
import org.example.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupResultSetMapperImpl implements GroupResultSetMapper {
    @Autowired
    TeacherRepository teacherRepository;
    @Override
    public GroupEntity map(ResultSet resultSet) throws SQLException {
        try {
            return new GroupEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("start_date"),
                    resultSet.getDate("end_date"),
                    teacherRepository.findById(resultSet.getInt("teacher_id"))
            );
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}