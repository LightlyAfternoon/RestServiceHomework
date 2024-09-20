package org.example.service.impl;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.repository.TeacherRepository;
import org.example.repository.impl.TeacherRepositoryImpl;
import org.example.service.TeacherService;

import java.sql.SQLException;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {
    TeacherRepository repository;

    public TeacherServiceImpl() {
        this.repository = new TeacherRepositoryImpl();
    }

    public TeacherServiceImpl(TeacherRepository repository) {
        this.repository = repository;
    }

    public TeacherServiceImpl(String path) {
        this.repository = new TeacherRepositoryImpl(path);
    }

    @Override
    public TeacherEntity findById(int id) throws SQLException {
        return repository.findById(id);
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return repository.deleteById(id);
    }

    @Override
    public TeacherEntity save(TeacherEntity entity) throws SQLException {
        return repository.save(entity);
    }

    @Override
    public List<TeacherEntity> findAll() throws SQLException {
        return repository.findAll();
    }

    @Override
    public List<GroupEntity> findAllGroupsWithTeacherId(int id) throws SQLException {
        return repository.findAllGroupsWithTeacherId(id);
    }

    @Override
    public List<SubjectEntity> findAllSubjectsWithTeacherId(int id) throws SQLException {
        return repository.findAllSubjectsWithTeacherId(id);
    }

    @Override
    public List<ExamEntity> findAllExamsWithTeacherId(int id) throws SQLException {
        return repository.findAllExamsWithTeacherId(id);
    }
}