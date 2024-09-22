package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.ExamEntity;
import org.example.repository.ExamRepository;
import org.example.repository.mapper.ExamResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamRepositoryImpl implements ExamRepository {
    public ExamRepositoryImpl() {}

    @Override
    public ExamEntity findById(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM exam WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new ExamResultSetMapperImpl().map(resultSet);
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM exam WHERE id = ?")) {

            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public ExamEntity save(ExamEntity entity) throws SQLException {
        if (entity.getId() > 0) {
            return update(entity);
        } else {
            return insert(entity);
        }
    }

    private ExamEntity insert(ExamEntity entity) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO exam (start_date, group_id, subject_teacher_id) VALUES (?, ?, ?)")) {

            preparedStatement.setDate(1, entity.getStartDate());
            preparedStatement.setInt(2, entity.getGroupId());
            preparedStatement.setInt(3, entity.getSubjectTeacherId());
            preparedStatement.executeUpdate();

            try (PreparedStatement newPreparedStatement = connection.prepareStatement("SELECT * FROM exam ORDER BY id DESC LIMIT 1")) {
                ResultSet resultSet = newPreparedStatement.executeQuery();

                if (!resultSet.next()) {
                    return null;
                }

                return new ExamResultSetMapperImpl().map(resultSet);
            }
        }
    }

    private ExamEntity update(ExamEntity entity) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE exam SET start_date = ?, group_id = ?, subject_teacher_id = ? WHERE id = ?")) {

            preparedStatement.setDate(1, entity.getStartDate());
            preparedStatement.setInt(2, entity.getGroupId());
            preparedStatement.setInt(3, entity.getSubjectTeacherId());
            preparedStatement.setInt(4, entity.getId());
            preparedStatement.executeUpdate();

            return entity;
        }
    }

    @Override
    public List<ExamEntity> findAll() throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM exam")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<ExamEntity> exams = new ArrayList<>();

            while (resultSet.next()) {
                exams.add(new ExamResultSetMapperImpl().map(resultSet));
            }

            return exams;
        }
    }
}