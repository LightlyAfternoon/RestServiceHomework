package org.example.service.impl;

import org.example.model.StudentEntity;
import org.example.repository.StudentRepository;
import org.example.service.StudentService;
import org.example.servlet.dto.StudentDTO;
import org.example.servlet.mapper.StudentDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;

    StudentDTOMapper studentMapper = StudentDTOMapper.INSTANCE;

    public StudentServiceImpl() {
    }

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentDTO findById(int id) throws SQLException {
        return studentMapper.mapToDTO(studentRepository.findById(id));
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return studentRepository.deleteById(id);
    }

    @Override
    public StudentDTO save(StudentEntity entity) throws SQLException {
        return studentMapper.mapToDTO(studentRepository.save(entity));
    }

    @Override
    public List<StudentDTO> findAll() throws SQLException {
        return studentRepository.findAll().stream().map(studentMapper::mapToDTO).toList();
    }
}