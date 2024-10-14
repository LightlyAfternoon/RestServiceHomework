package org.example.repository.mapper;

import org.example.model.ExamEntity;
import org.example.repository.GroupRepository;
import org.example.repository.SubjectRepository;
import org.example.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamResultSetMapperImpl implements ExamResultSetMapper {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    GroupRepository groupRepository;

    @Override
    public ExamEntity map(ResultSet resultSet) throws SQLException {
        try {
            return new ExamEntity(
                    resultSet.getInt("id"),
                    resultSet.getDate("start_date"),
                    groupRepository.findById(resultSet.getInt("group_id")),
                    subjectRepository.findById(resultSet.getInt("subject_id")),
                    teacherRepository.findById(resultSet.getInt("teacher_id"))
            );
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}