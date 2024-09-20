package org.example.service.impl;

import org.example.model.StudentEntity;
import org.example.repository.StudentRepository;
import org.example.repository.impl.StudentRepositoryImpl;
import org.example.service.StudentService;

import java.sql.SQLException;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    StudentRepository studentRepository;

    public StudentServiceImpl() {
        studentRepository = new StudentRepositoryImpl("C:\\Users\\Vika\\IdeaProjects\\Homeworks\\RestServiceHomework\\src\\main\\java\\org\\example\\db\\DbParameters");
    }

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentEntity findById(int id) throws SQLException {
        return studentRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return studentRepository.deleteById(id);
    }

    @Override
    public StudentEntity save(StudentEntity entity) throws SQLException {
        return studentRepository.save(entity);
    }

    @Override
    public List<StudentEntity> findAll() throws SQLException {
        return studentRepository.findAll();
    }
}