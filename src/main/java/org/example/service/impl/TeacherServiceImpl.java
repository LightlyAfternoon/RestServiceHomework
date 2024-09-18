package org.example.service.impl;

import org.example.model.TeacherEntity;
import org.example.repository.TeacherRepository;
import org.example.service.TeacherService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {
    TeacherRepository repository;

    public TeacherServiceImpl(TeacherRepository repository) {
        this.repository = repository;
    }

    @Override
    public TeacherEntity findById(int id) throws SQLException, IOException {
        return repository.findById(id);
    }

    @Override
    public boolean deleteById(int id) throws SQLException, IOException {
        return repository.deleteById(id);
    }

    @Override
    public TeacherEntity save(TeacherEntity entity) throws SQLException, IOException {
        return repository.save(entity);
    }

    @Override
    public List<TeacherEntity> findAll() throws SQLException, IOException {
        return repository.findAll();
    }
}