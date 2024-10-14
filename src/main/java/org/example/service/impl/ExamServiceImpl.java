package org.example.service.impl;

import org.example.model.ExamEntity;
import org.example.repository.ExamRepository;
import org.example.service.ExamService;
import org.example.servlet.dto.ExamDTO;
import org.example.servlet.mapper.ExamDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public class ExamServiceImpl implements ExamService {
    @Autowired
    ExamRepository examRepository;

    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;

    public ExamServiceImpl() {
    }

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
    public ExamDTO save(ExamEntity entity) throws SQLException {
        return examMapper.mapToDTO(examRepository.save(entity));
    }

    @Override
    public List<ExamDTO> findAll() throws SQLException {
        return examRepository.findAll().stream().map(examMapper::mapToDTO).toList();
    }
}