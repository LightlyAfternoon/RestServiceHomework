package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.TeacherEntity;
import org.example.repository.TeacherRepository;
import org.example.repository.mapper.TeacherResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class TeacherRepositoryImpl implements TeacherRepository {
    @Override
    public TeacherEntity findById(int id) {
        try (Connection connection = new ConnectionManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM teacher WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return new TeacherResultSetMapperImpl().map(resultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection connection = new ConnectionManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM teacher WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TeacherEntity save(TeacherEntity entity) {

    }

    @Override
    public List<TeacherEntity> findAll() {

    }
}