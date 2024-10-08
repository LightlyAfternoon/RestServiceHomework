package org.example.service;

import org.example.model.StudentEntity;
import org.example.repository.StudentRepository;
import org.example.repository.impl.StudentRepositoryImpl;
import org.example.service.impl.StudentServiceImpl;
import org.example.servlet.mapper.StudentDTOMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class StudentServiceImplTest {
    StudentRepository mockStudentRepository;
    StudentService studentService;
    StudentEntity studentEntity;

    StudentDTOMapper studentMapper = StudentDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockStudentRepository = Mockito.mock(StudentRepositoryImpl.class);
        studentService = new StudentServiceImpl(mockStudentRepository);

        studentEntity = new StudentEntity(1, "Клара", "Ломоносова", "Евгеньевна", 1);
    }

    @Test
    void findByIdTest() throws SQLException {
        Mockito.when(mockStudentRepository.findById(1)).thenReturn(studentEntity);

        Assertions.assertEquals(studentService.findById(1), studentMapper.mapToDTO(studentEntity));

        studentEntity = new StudentEntity(2, "Клара", "Ломоносова", "Евгеньевна", 3);

        Assertions.assertNotEquals(studentService.findById(1), studentMapper.mapToDTO(studentEntity));
    }

    @Test
    void deleteByIdTest() throws SQLException {
        Mockito.when(mockStudentRepository.deleteById(1)).thenReturn(true);
        Mockito.when(mockStudentRepository.deleteById(2)).thenReturn(false);

        Assertions.assertTrue(studentService.deleteById(1));
        Assertions.assertFalse(studentService.deleteById(2));
    }

    @Test
    void findAllTest() throws SQLException {
        List<StudentEntity> studentEntities = new ArrayList<>();
        studentEntities.add(studentEntity);

        Mockito.when(mockStudentRepository.findAll()).thenReturn(studentEntities);

        Assertions.assertEquals(studentService.findAll(), studentEntities.stream().map(studentMapper::mapToDTO).toList());
    }

    @Test
    void saveStudentTest() throws SQLException {
        studentEntity = new StudentEntity(2, "Клавдий", "Ломоносов", "Германович", 1);

        Mockito.when(mockStudentRepository.save(studentEntity)).thenReturn(studentEntity);

        studentEntity = new StudentEntity(2, "Клавдий", "Ломоносов", "Германович", 1);

        Assertions.assertEquals(studentMapper.mapToDTO(studentEntity), studentService.save(studentEntity));
    }
}