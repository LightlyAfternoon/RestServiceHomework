package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.StudentEntity;
import org.example.repository.StudentRepository;
import org.example.repository.mapper.StudentResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryImpl implements StudentRepository {
    public StudentRepositoryImpl() {}

    public StudentRepositoryImpl(String path) {
        ConnectionManager.setConfig(path);
    }

    @Override
    public StudentEntity findById(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM student WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new StudentResultSetMapperImpl().map(resultSet);
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM student WHERE id = ?")) {

            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public StudentEntity save(StudentEntity entity) throws SQLException {
        if (entity.getId() > 0) {
            return update(entity);
        } else {
            return insert(entity);
        }
    }

    private StudentEntity insert(StudentEntity entity) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                     "student(first_name, last_name, patronymic, group_id) VALUES (?, ?, ?, ?)")) {

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getPatronymic());
            preparedStatement.setInt(4, entity.getGroupId());

            preparedStatement.executeUpdate();

            try (PreparedStatement newPreparedStatement = connection.prepareStatement("SELECT * FROM student ORDER BY id DESC LIMIT 1")) {
                ResultSet resultSet = newPreparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

                return new StudentResultSetMapperImpl().map(resultSet);
            }
        }
    }

    private StudentEntity update(StudentEntity entity) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE student SET " +
                     "first_name = ?, last_name = ?, patronymic = ?, group_id = ? WHERE id = ?")) {

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getPatronymic());
            preparedStatement.setInt(4, entity.getGroupId());
            preparedStatement.setInt(5, entity.getId());

            preparedStatement.executeUpdate();

            return entity;
        }
    }

    @Override
    public List<StudentEntity> findAll() throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM student")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<StudentEntity> students = new ArrayList<>();

            while (resultSet.next()) {
                students.add(new StudentResultSetMapperImpl().map(resultSet));
            }

            return students;
        }
    }
}