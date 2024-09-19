package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.repository.SubjectRepository;
import org.example.repository.mapper.ExamResultSetMapperImpl;
import org.example.repository.mapper.GroupResultSetMapperImpl;
import org.example.repository.mapper.SubjectResultSetMapperImpl;
import org.example.repository.mapper.TeacherResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SubjectRepositoryImpl implements SubjectRepository {
    @Override
    public SubjectEntity findById(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM subject WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            SubjectEntity subjectEntity = new SubjectResultSetMapperImpl().map(resultSet);
            subjectEntity.setTeachers(findAllTeachersWithSubjectId(subjectEntity.getId()));
            subjectEntity.setGroups(findAllGroupsWithSubjectId(subjectEntity.getId()));
            subjectEntity.setExams(findAllExamsWithSubjectId(subjectEntity.getId()));

            return subjectEntity;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM subject WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public SubjectEntity save(SubjectEntity entity) throws SQLException {
        if (entity.getId() > 0) {
            return update(entity);
        } else {
            return insert(entity);
        }
    }

    private SubjectEntity insert(SubjectEntity entity) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO subject (name) VALUES (?)")) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.executeUpdate();

            try (PreparedStatement newPreparedStatement = connection.prepareStatement("SELECT * FROM subject ORDER BY id DESC LIMIT 1")) {
                ResultSet resultSet = newPreparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

                SubjectEntity subject = new SubjectResultSetMapperImpl().map(resultSet);
                subject.setTeachers(findAllTeachersWithSubjectId(subject.getId()));

                return  subject;
            }
        }
    }

    private SubjectEntity update(SubjectEntity entity) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE subject SET name = ? WHERE id = ?")) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.executeUpdate();

            return entity;
        }
    }

    @Override
    public TeacherEntity save(SubjectEntity subject, TeacherEntity teacher) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO subject_teacher (subject_id, teacher_id) VALUES (?, ?)")) {

            preparedStatement.setInt(1, subject.getId());
            preparedStatement.setInt(2, teacher.getId());

            preparedStatement.executeUpdate();

            return teacher;
        }
    }

    @Override
    public GroupEntity save(SubjectEntity subject, GroupEntity group) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO subject_group (subject_id, group_id) VALUES (?, ?)")) {

            preparedStatement.setInt(1, subject.getId());
            preparedStatement.setInt(2, group.getId());

            preparedStatement.executeUpdate();

            return group;
        }
    }

    @Override
    public List<SubjectEntity> findAll() throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM subject")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<SubjectEntity> subjectEntities = new ArrayList<>();

            while (resultSet.next()) {
                SubjectEntity subjectEntity = new SubjectResultSetMapperImpl().map(resultSet);
                subjectEntity.setTeachers(findAllTeachersWithSubjectId(subjectEntity.getId()));

                subjectEntities.add(subjectEntity);
            }

            return subjectEntities;
        }
    }

    @Override
    public List<TeacherEntity> findAllTeachersWithSubjectId(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT t.* FROM subject_teacher st " +
                     "JOIN teacher t ON st.teacher_id = t.id WHERE st.subject_id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TeacherEntity> teacherEntities = new ArrayList<>();

            while (resultSet.next()) {
                teacherEntities.add(new TeacherResultSetMapperImpl().map(resultSet));
            }

            return teacherEntities;
        }
    }

    @Override
    public List<ExamEntity> findAllExamsWithSubjectId(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT e.* FROM subject_teacher st " +
                     "JOIN exam e ON st.id = e.subject_teacher_id WHERE st.subject_id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ExamEntity> examEntities = new ArrayList<>();

            while (resultSet.next()) {
                examEntities.add(new ExamResultSetMapperImpl().map(resultSet));
            }

            return examEntities;
        }
    }

    @Override
    public List<GroupEntity> findAllGroupsWithSubjectId(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT g.* FROM subject_group sg " +
                     "JOIN \"group\" g ON g.id = sg.group_id WHERE sg.subject_id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<GroupEntity> groupEntities = new ArrayList<>();

            while (resultSet.next()) {
                groupEntities.add(new GroupResultSetMapperImpl().map(resultSet));
            }

            return groupEntities;
        }
    }
}