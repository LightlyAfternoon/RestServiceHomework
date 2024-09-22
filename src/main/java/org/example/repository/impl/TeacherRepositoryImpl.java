package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.repository.TeacherRepository;
import org.example.repository.mapper.ExamResultSetMapperImpl;
import org.example.repository.mapper.GroupResultSetMapperImpl;
import org.example.repository.mapper.SubjectResultSetMapperImpl;
import org.example.repository.mapper.TeacherResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepositoryImpl implements TeacherRepository {
    public TeacherRepositoryImpl() {}

    @Override
    public TeacherEntity findById(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM teacher WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            TeacherEntity teacherEntity = new TeacherResultSetMapperImpl().map(resultSet);
            teacherEntity.setSubjects(findAllSubjectsWithTeacherId(teacherEntity.getId()));
            teacherEntity.setGroups(findAllGroupsWithTeacherId(teacherEntity.getId()));
            teacherEntity.setExams(findAllExamsWithTeacherId(teacherEntity.getId()));

            return teacherEntity;

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM teacher WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public TeacherEntity save(TeacherEntity entity) throws SQLException {
        if (entity.getId() > 0) {
            return update(entity);
        } else {
            return insert(entity);
        }
    }

    private TeacherEntity insert(TeacherEntity entity) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO teacher (first_name, last_name, patronymic) VALUES(?, ?, ?)")) {

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getPatronymic());

            preparedStatement.executeUpdate();

            ResultSet resultSet;
            try (PreparedStatement newPreparedStatement = connection.prepareStatement("SELECT * FROM teacher ORDER BY id DESC LIMIT 1")) {
                resultSet = newPreparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

                TeacherEntity teacher = new TeacherResultSetMapperImpl().map(resultSet);
                teacher.setSubjects(findAllSubjectsWithTeacherId(teacher.getId()));

                return teacher;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private TeacherEntity update(TeacherEntity entity) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE teacher SET first_name = ?, last_name = ?, patronymic = ? WHERE id = ?")) {

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getPatronymic());
            preparedStatement.setInt(4, entity.getId());

            preparedStatement.executeUpdate();

            return entity;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<TeacherEntity> findAll() throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM teacher")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<TeacherEntity> teacherEntities = new ArrayList<>();

            while (resultSet.next()) {
                TeacherEntity teacherEntity = new TeacherResultSetMapperImpl().map(resultSet);
                teacherEntity.setSubjects(findAllSubjectsWithTeacherId(teacherEntity.getId()));

                teacherEntities.add(teacherEntity);
            }

            return teacherEntities;
        }
    }

    @Override
    public List<GroupEntity> findAllGroupsWithTeacherId(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"group\" WHERE teacher_id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<GroupEntity> groupEntities = new ArrayList<>();

            while (resultSet.next()) {
                groupEntities.add(new GroupResultSetMapperImpl().map(resultSet));
            }

            return groupEntities;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<SubjectEntity> findAllSubjectsWithTeacherId(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT s.* FROM subject_teacher st " +
                     "JOIN subject s ON s.id = st.subject_id WHERE st.teacher_id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<SubjectEntity> subjectEntities = new ArrayList<>();

            while (resultSet.next()) {
                subjectEntities.add(new SubjectResultSetMapperImpl().map(resultSet));
            }

            return subjectEntities;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<ExamEntity> findAllExamsWithTeacherId(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT e.* FROM subject_teacher st " +
                     "JOIN exam e ON st.id = e.subject_teacher_id WHERE st.teacher_id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ExamEntity> examEntities = new ArrayList<>();

            while (resultSet.next()) {
                examEntities.add(new ExamResultSetMapperImpl().map(resultSet));
            }

            return examEntities;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}