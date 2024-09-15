package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.repository.TeacherRepository;
import org.example.repository.mapper.SubjectResultSetMapperImpl;
import org.example.repository.mapper.TeacherResultSetMapperImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TeacherRepositoryImpl implements TeacherRepository {
    @Override
    public TeacherEntity findById(int id) throws SQLException, IOException {
        try (Connection connection = new ConnectionManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM teacher WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            TeacherEntity teacherEntity = new TeacherResultSetMapperImpl().map(resultSet);
            teacherEntity.setSubjects(findAllSubjectsWithTeacherId(teacherEntity.getId()));

            return teacherEntity;
        } catch (SQLException e) {
            throw new SQLException(e);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException, IOException {
        try (Connection connection = new ConnectionManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM teacher WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Override
    public TeacherEntity save(TeacherEntity entity) throws SQLException, IOException {
        String sql;
        int id = entity.getId();

        if (id > 0) {
            sql = "UPDATE teacher SET first_name = ?, last_name = ?, patronymic = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO teacher (first_name, last_name, patronymic) VALUES(?, ?, ?)";
        }

        try (Connection connection = new ConnectionManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getPatronymic());
            if (id > 0) {
                preparedStatement.setInt(4, id);
            }

            preparedStatement.executeUpdate();

            if (id < 1) {
                ResultSet resultSet;
                try (PreparedStatement newPreparedStatement = connection.prepareStatement("SELECT * FROM teacher ORDER BY id DESC LIMIT 1")) {
                    resultSet = newPreparedStatement.executeQuery();
                    resultSet.next();

                    TeacherEntity teacher = new TeacherResultSetMapperImpl().map(resultSet);
                    teacher.setSubjects(findAllSubjectsWithTeacherId(teacher.getId()));

                    entity = teacher;
                }
            }

            return entity;
        } catch (SQLException e) {
            throw new SQLException(e);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Override
    public List<TeacherEntity> findAll() throws SQLException, IOException {
        try (Connection connection = new ConnectionManager().getConnection();
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
    public List<SubjectEntity> findAllSubjectsWithTeacherId(int id) throws SQLException, IOException {
        try (Connection connection = new ConnectionManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM subject_teacher WHERE teacher_id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<SubjectEntity> subjectEntities = new ArrayList<>();

            while (resultSet.next()) {
                try (PreparedStatement newPreparedStatement = connection.prepareStatement("SELECT * FROM subject WHERE id = ?")) {
                    newPreparedStatement.setInt(1, resultSet.getInt("subject_id"));
                    ResultSet newResultSet = newPreparedStatement.executeQuery();
                    newResultSet.next();

                    subjectEntities.add(new SubjectResultSetMapperImpl().map(newResultSet));
                }
            }

            return subjectEntities;
        } catch (SQLException e) {
            throw new SQLException(e);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}