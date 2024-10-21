package org.example.service.impl;

import org.example.controller.dto.GradeDTO;
import org.example.controller.mapper.GradeDTOMapper;
import org.example.repository.ExamRepository;
import org.example.service.ExamService;
import org.example.controller.dto.ExamDTO;
import org.example.controller.mapper.ExamDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {
    ExamRepository examRepository;

    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;
    GradeDTOMapper gradeMapper = GradeDTOMapper.INSTANCE;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public ExamDTO findById(int id) throws SQLException {
        return examMapper.mapToDTO(examRepository.findById(id));
    }

    @Override
    public void deleteById(int id) throws SQLException {
        examRepository.deleteById(id);
    }

    @Override
    public ExamDTO save(ExamDTO examDTO) throws SQLException {
        return examMapper.mapToDTO(examRepository.save(examMapper.mapToEntity(examDTO)));
    }

    @Override
    public ExamDTO save(ExamDTO examDTO, int id) throws SQLException {
        return examMapper.mapToDTO(examRepository.save(examMapper.mapToEntity(examDTO, id)));
    }

    @Override
    public Set<ExamDTO> findAll() throws SQLException {
        return examRepository.findAll().stream().map(examMapper::mapToDTO).collect(Collectors.toSet());
    }

    @Override
    public Set<GradeDTO> findAllGradesWithExamId(int id) throws SQLException {
        return examRepository.findById(id).getGrades().stream().map(gradeMapper::mapToDTO).collect(Collectors.toSet());
    }
}