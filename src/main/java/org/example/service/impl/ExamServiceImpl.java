package org.example.service.impl;

import org.example.repository.ExamRepository;
import org.example.service.ExamService;
import org.example.servlet.dto.ExamDTO;
import org.example.servlet.mapper.ExamDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

public class ExamServiceImpl implements ExamService {
    ExamRepository examRepository;

    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public ExamDTO findById(int id) throws SQLException {
        return examMapper.mapToDTO(examRepository.findById(id));
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return examRepository.deleteById(id);
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
}