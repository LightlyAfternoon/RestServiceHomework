package org.example.service.impl;

import org.example.model.GradeEntity;
import org.example.model.GroupEntity;
import org.example.repository.GradeRepository;
import org.example.repository.impl.GradeRepositoryImpl;
import org.example.service.GradeService;

import java.io.IOException;
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

    @Override
    public GradeEntity findById(int id) throws SQLException, IOException {
        return gradeRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) throws SQLException, IOException {
        return gradeRepository.deleteById(id);
    }

    @Override
    public GradeEntity save(GradeEntity entity) throws SQLException, IOException {
        return gradeRepository.save(entity);
    }

    @Override
    public List<GradeEntity> findAll() throws SQLException, IOException {
        return gradeRepository.findAll();
    }
}