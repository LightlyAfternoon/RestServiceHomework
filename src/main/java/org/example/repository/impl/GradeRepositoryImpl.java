package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.GradeEntity;
import org.example.repository.GradeRepository;
import org.example.repository.mapper.GradeResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeRepositoryImpl implements GradeRepository {
    @Override
    public GradeEntity findById(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM grade WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return new GradeResultSetMapperImpl().map(resultSet);
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM grade WHERE id = ?")) {

            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public GradeEntity save(GradeEntity entity) throws SQLException {
        if (entity.getId() > 0) {
            return update(entity);
        } else {
            return insert(entity);
        }
    }

    private GradeEntity insert(GradeEntity entity) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                     "grade(student_id, exam_id, mark) VALUES (?, ?, ?)")) {

            preparedStatement.setInt(1, entity.getStudentId());
            preparedStatement.setInt(2, entity.getExamId());
            preparedStatement.setShort(3, entity.getMark());

            preparedStatement.executeUpdate();

            try (PreparedStatement newPreparedStatement = connection.prepareStatement("SELECT * FROM grade ORDER BY id DESC LIMIT 1")) {
                ResultSet resultSet = newPreparedStatement.executeQuery();
                resultSet.next();

                return new GradeResultSetMapperImpl().map(resultSet);
            }
        }
    }

    private GradeEntity update(GradeEntity entity) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE grade SET " +
                     "student_id = ?, exam_id = ?, mark = ? WHERE id = ?")) {

            preparedStatement.setInt(1, entity.getStudentId());
            preparedStatement.setInt(2, entity.getExamId());
            preparedStatement.setShort(3, entity.getMark());
            preparedStatement.setInt(4, entity.getId());

            preparedStatement.executeUpdate();

            return entity;
        }
    }

    @Override
    public List<GradeEntity> findAll() throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM grade")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<GradeEntity> grades = new ArrayList<>();

            while (resultSet.next()) {
                grades.add(new GradeResultSetMapperImpl().map(resultSet));
            }

            return grades;
        }
    }
}