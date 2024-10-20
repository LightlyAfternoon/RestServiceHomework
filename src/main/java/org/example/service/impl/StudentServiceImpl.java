package org.example.service.impl;

import org.example.controller.dto.GradeDTO;
import org.example.controller.mapper.GradeDTOMapper;
import org.example.repository.StudentRepository;
import org.example.service.StudentService;
import org.example.controller.dto.StudentDTO;
import org.example.controller.mapper.StudentDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    StudentRepository studentRepository;

    StudentDTOMapper studentMapper = StudentDTOMapper.INSTANCE;
    GradeDTOMapper gradeMapper = GradeDTOMapper.INSTANCE;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentDTO findById(int id) throws SQLException {
        return studentMapper.mapToDTO(studentRepository.findById(id));
    }

    @Override
    public void deleteById(int id) throws SQLException {
        studentRepository.deleteById(id);
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

    @Override
    public Set<GradeDTO> findAllGradesWithServiceId(int id) throws SQLException {
        return studentRepository.findById(id).getGrades().stream().map(gradeMapper::mapToDTO).collect(Collectors.toSet());
    }
}