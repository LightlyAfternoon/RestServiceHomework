package org.example.service.impl;

import org.example.model.StudentEntity;
import org.example.repository.StudentRepository;
import org.example.repository.impl.StudentRepositoryImpl;
import org.example.service.StudentService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    StudentRepository studentRepository;

    public StudentServiceImpl() {
        studentRepository = new StudentRepositoryImpl();
    }

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentEntity findById(int id) throws SQLException, IOException {
        return studentRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) throws SQLException, IOException {
        return studentRepository.deleteById(id);
    }

    @Override
    public StudentEntity save(StudentEntity entity) throws SQLException, IOException {
        return studentRepository.save(entity);
    }

    @Override
    public List<StudentEntity> findAll() throws SQLException, IOException {
        return studentRepository.findAll();
    }
}