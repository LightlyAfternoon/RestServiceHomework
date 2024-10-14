package org.example.repository.mapper;

import org.example.model.GradeEntity;
import org.example.repository.ExamRepository;
import org.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeResultSetMapperImpl implements GradeResultSetMapper {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ExamRepository examRepository;

    @Override
    public GradeEntity map(ResultSet resultSet) throws SQLException {
        try {
            return new GradeEntity(
                    resultSet.getInt("id"),
                    studentRepository.findById(resultSet.getInt("student_id")),
                    examRepository.findById(resultSet.getInt("exam_id")),
                    resultSet.getShort("mark")
            );
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}