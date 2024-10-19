package org.example.service.impl;

import org.example.repository.GradeRepository;
import org.example.service.GradeService;
import org.example.controller.dto.GradeDTO;
import org.example.controller.mapper.GradeDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements GradeService {
    GradeRepository gradeRepository;

    GradeDTOMapper gradeMapper = GradeDTOMapper.INSTANCE;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public GradeDTO findById(int id) throws SQLException {
        return gradeMapper.mapToDTO(gradeRepository.findById(id));
    }

    @Override
    public void deleteById(int id) throws SQLException {
        gradeRepository.deleteById(id);
    }

    @Override
    public GradeDTO save(GradeDTO gradeDTO) throws SQLException {
        return gradeMapper.mapToDTO(gradeRepository.save(gradeMapper.mapToEntity(gradeDTO)));
    }

    @Override
    public GradeDTO save(GradeDTO gradeDTO, int id) throws SQLException {
        return gradeMapper.mapToDTO(gradeRepository.save(gradeMapper.mapToEntity(gradeDTO, id)));
    }

    @Override
    public Set<GradeDTO> findAll() throws SQLException {
        return gradeRepository.findAll().stream().map(gradeMapper::mapToDTO).collect(Collectors.toSet());
    }
}