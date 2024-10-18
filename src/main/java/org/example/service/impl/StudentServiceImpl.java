package org.example.service.impl;

import org.example.repository.StudentRepository;
import org.example.service.StudentService;
import org.example.servlet.dto.StudentDTO;
import org.example.servlet.mapper.StudentDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {
    StudentRepository studentRepository;

    StudentDTOMapper studentMapper = StudentDTOMapper.INSTANCE;

    @Autowired
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
    public StudentDTO save(StudentDTO studentDTO) throws SQLException {
        return studentMapper.mapToDTO(studentRepository.save(studentMapper.mapToEntity(studentDTO)));
    }

    @Override
    public StudentDTO save(StudentDTO studentDTO, int id) throws SQLException {
        return studentMapper.mapToDTO(studentRepository.save(studentMapper.mapToEntity(studentDTO, id)));
    }

    @Override
    public Set<StudentDTO> findAll() throws SQLException {
        return studentRepository.findAll().stream().map(studentMapper::mapToDTO).collect(Collectors.toSet());
    }
}