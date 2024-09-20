package org.example.service.impl;

import org.example.model.GradeEntity;
import org.example.repository.GradeRepository;
import org.example.repository.impl.GradeRepositoryImpl;
import org.example.service.GradeService;

import java.sql.SQLException;
import java.util.List;

public class GradeServiceImpl implements GradeService {
    GradeRepository gradeRepository;

    public GradeServiceImpl() {
        gradeRepository = new GradeRepositoryImpl();
    }

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public GradeServiceImpl(String path) {
        this.gradeRepository = new GradeRepositoryImpl(path);
    }

    @Override
    public GradeEntity findById(int id) throws SQLException {
        return gradeRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return gradeRepository.deleteById(id);
    }

    @Override
    public GradeEntity save(GradeEntity entity) throws SQLException {
        return gradeRepository.save(entity);
    }

    @Override
    public List<GradeEntity> findAll() throws SQLException {
        return gradeRepository.findAll();
    }
}