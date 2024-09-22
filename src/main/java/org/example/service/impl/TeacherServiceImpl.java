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
    TeacherRepository teacherRepository;

    public TeacherServiceImpl() {
        this.teacherRepository = new TeacherRepositoryImpl();
    }

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public TeacherEntity findById(int id) throws SQLException {
        return teacherRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return teacherRepository.deleteById(id);
    }

    @Override
    public TeacherEntity save(TeacherEntity entity) throws SQLException {
        return teacherRepository.save(entity);
    }

    @Override
    public List<TeacherEntity> findAll() throws SQLException {
        return teacherRepository.findAll();
    }

    @Override
    public List<GroupEntity> findAllGroupsWithTeacherId(int id) throws SQLException {
        return teacherRepository.findAllGroupsWithTeacherId(id);
    }

    @Override
    public List<SubjectEntity> findAllSubjectsWithTeacherId(int id) throws SQLException {
        return teacherRepository.findAllSubjectsWithTeacherId(id);
    }

    @Override
    public List<ExamEntity> findAllExamsWithTeacherId(int id) throws SQLException {
        return teacherRepository.findAllExamsWithTeacherId(id);
    }
}