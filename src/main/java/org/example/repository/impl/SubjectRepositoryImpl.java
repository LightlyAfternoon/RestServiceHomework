package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.repository.SubjectRepository;
import org.example.repository.mapper.SubjectResultSetMapperImpl;
import org.example.repository.mapper.TeacherResultSetMapperImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepositoryImpl implements SubjectRepository {
    @Override
    public SubjectEntity findById(int id) throws SQLException, IOException {
        try (Connection connection = new ConnectionManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM subject WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            SubjectEntity subjectEntity = new SubjectResultSetMapperImpl().map(resultSet);
            subjectEntity.setTeachers(findAllTeachersWithSubjectId(subjectEntity.getId()));

            return subjectEntity;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException, IOException {
        try (Connection connection = new ConnectionManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM subject WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public SubjectEntity save(SubjectEntity entity) throws SQLException, IOException {
        String sql;
        int id = entity.getId();

        if (id > 0) {
            sql = "UPDATE subject SET name = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO subject (name) VALUES (?)";
        }

        try (Connection connection = new ConnectionManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getName());
            if (id > 0) {
                preparedStatement.setInt(2, id);
            }

            preparedStatement.executeUpdate();

            if (id < 1) {
                try (PreparedStatement newPreparedStatement = connection.prepareStatement("SELECT * FROM subject ORDER BY id DESC LIMIT 1")) {
                    ResultSet resultSet = newPreparedStatement.executeQuery();
                    resultSet.next();

                    SubjectEntity subject = new SubjectResultSetMapperImpl().map(resultSet);
                    subject.setTeachers(findAllTeachersWithSubjectId(subject.getId()));

                    entity = subject;
                }
            }

            return entity;
        }
    }

    @Override
    public List<SubjectEntity> findAll() throws SQLException, IOException {
        try (Connection connection = new ConnectionManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM subject")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<SubjectEntity> subjectEntities = new ArrayList<>();

            while (resultSet.next()) {
                subjectEntities.add(new SubjectResultSetMapperImpl().map(resultSet));
            }

            return subjectEntities;
        }
    }

    @Override
    public List<TeacherEntity> findAllTeachersWithSubjectId(int id) throws SQLException, IOException {
        try (Connection connection = new ConnectionManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM subject_teacher WHERE subject_id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TeacherEntity> teacherEntities = new ArrayList<>();

            while (resultSet.next()) {
                try (PreparedStatement newPreparedStatement = connection.prepareStatement("SELECT * FROM teacher WHERE id = ?")) {
                    newPreparedStatement.setInt(1, resultSet.getInt("teacher_id"));
                    ResultSet newResultSet = newPreparedStatement.executeQuery();
                    newResultSet.next();

                    teacherEntities.add(new TeacherResultSetMapperImpl().map(newResultSet));
                }
            }

            return teacherEntities;
        }
    }
}