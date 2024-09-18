package org.example.service;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.StudentEntity;
import org.example.repository.StudentRepository;
import org.example.repository.impl.StudentRepositoryImpl;
import org.example.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

class StudentServiceImplTest {
    StudentRepository mockStudentRepository;
    StudentService studentService;
    StudentEntity studentEntity;

    @BeforeEach
    void setUp() {
        mockStudentRepository = Mockito.mock(StudentRepositoryImpl.class);
        studentService = new StudentServiceImpl(mockStudentRepository);

        studentEntity = new StudentEntity(1, "Клара", "Ломоносова", "Евгеньевна", 1);
    }

    @Test
    void findByIdTest() throws SQLException, IOException {
        Mockito.when(mockStudentRepository.findById(1)).thenReturn(studentEntity);

        Assertions.assertEquals(studentService.findById(1), studentEntity);

        studentEntity = new StudentEntity(2, "Клара", "Ломоносова", "Евгеньевна", 3);

        Assertions.assertNotEquals(studentService.findById(1), studentEntity);
    }

    @Test
    void deleteByIdTest() throws SQLException, IOException {
        Mockito.when(mockStudentRepository.deleteById(1)).thenReturn(true);
        Mockito.when(mockStudentRepository.deleteById(2)).thenReturn(false);

        Assertions.assertTrue(studentService.deleteById(1));
        Assertions.assertFalse(studentService.deleteById(2));
    }

    @Test
    void findAllTest() throws SQLException, IOException {
        List<StudentEntity> studentEntities = new ArrayList<>();
        studentEntities.add(studentEntity);

        Mockito.when(mockStudentRepository.findAll()).thenReturn(studentEntities);

        Assertions.assertEquals(studentService.findAll(), studentEntities);
    }

    @Test
    void saveStudentTest() throws SQLException, IOException {
        studentEntity = new StudentEntity(2, "Клавдий", "Ломоносов", "Германович", 1);

        Mockito.when(mockStudentRepository.save(studentEntity)).thenReturn(studentEntity);

        studentEntity = new StudentEntity(2, "Клавдий", "Ломоносов", "Германович", 1);

        Assertions.assertEquals(studentEntity, studentService.save(studentEntity));
    }
}