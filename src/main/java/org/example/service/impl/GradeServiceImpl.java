package org.example.service.impl;

import org.example.model.GradeEntity;
import org.example.repository.GradeRepository;
import org.example.repository.impl.GradeRepositoryImpl;
import org.example.service.GradeService;
import org.example.servlet.dto.GradeDTO;
import org.example.servlet.mapper.GradeDTOMapper;

import java.sql.SQLException;
import java.util.List;

public class GradeServiceImpl implements GradeService {
    GradeRepository gradeRepository;

    GradeDTOMapper gradeMapper = GradeDTOMapper.INSTANCE;

    public GradeServiceImpl() {
        gradeRepository = new GradeRepositoryImpl();
    }

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public GradeDTO findById(int id) throws SQLException {
        return gradeMapper.mapToDTO(gradeRepository.findById(id));
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return gradeRepository.deleteById(id);
    }

    @Override
    public GradeDTO save(GradeEntity entity) throws SQLException {
        return gradeMapper.mapToDTO(gradeRepository.save(entity));
    }

    @Override
    public List<GradeDTO> findAll() throws SQLException {
        return gradeRepository.findAll().stream().map(gradeMapper::mapToDTO).toList();
    }
}