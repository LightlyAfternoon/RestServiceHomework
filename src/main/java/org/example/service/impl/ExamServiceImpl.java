package org.example.service.impl;

import org.example.model.ExamEntity;
import org.example.repository.ExamRepository;
import org.example.repository.impl.ExamRepositoryImpl;
import org.example.service.ExamService;

import java.sql.SQLException;
import java.util.List;

public class ExamServiceImpl implements ExamService {
    ExamRepository examRepository;

    public ExamServiceImpl() {
        examRepository = new ExamRepositoryImpl();
    }

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public ExamEntity findById(int id) throws SQLException {
        return examRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return examRepository.deleteById(id);
    }

    @Override
    public ExamEntity save(ExamEntity entity) throws SQLException {
        return examRepository.save(entity);
    }

    @Override
    public List<ExamEntity> findAll() throws SQLException {
        return examRepository.findAll();
    }
}