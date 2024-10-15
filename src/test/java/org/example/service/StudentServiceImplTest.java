package org.example.service;

import org.example.model.GroupEntity;
import org.example.model.StudentEntity;
import org.example.model.TeacherEntity;
import org.example.repository.StudentRepository;
import org.example.service.impl.StudentServiceImpl;
import org.example.servlet.mapper.StudentDTOMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

class StudentServiceImplTest {
    StudentRepository mockStudentRepository;
    StudentService studentService;
    StudentEntity student;
    GroupEntity group;

    StudentDTOMapper studentMapper = StudentDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockStudentRepository = Mockito.mock(StudentRepository.class);
        studentService = new StudentServiceImpl(mockStudentRepository);


        TeacherEntity teacher = new TeacherEntity(1, "t", "t", "t");
        group = new GroupEntity(1, "П-0",
                new Date(new GregorianCalendar(2015, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2019, Calendar.JUNE, 30).getTimeInMillis()),
                teacher);
        student = new StudentEntity(1, "Клара", "Ломоносова", "Евгеньевна", group);
    }

    @Test
    void findByIdTest() throws SQLException {
        Mockito.when(mockStudentRepository.findById(1)).thenReturn(student);

        Assertions.assertEquals(studentService.findById(1), studentMapper.mapToDTO(student));

        group.setId(3);
        student = new StudentEntity(2, "Клара", "Ломоносова", "Евгеньевна", group);

        Assertions.assertNotEquals(studentService.findById(1), studentMapper.mapToDTO(student));
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
        studentEntities.add(student);

        Mockito.when(mockStudentRepository.findAll()).thenReturn(studentEntities);

        Assertions.assertEquals(studentService.findAll(), studentEntities.stream().map(studentMapper::mapToDTO).toList());
    }

    @Test
    void saveStudentTest() throws SQLException {
        student = new StudentEntity(2, "Клавдий", "Ломоносов", "Германович", group);

        Mockito.when(mockStudentRepository.save(student)).thenReturn(student);

        student = new StudentEntity(2, "Клавдий", "Ломоносов", "Германович", group);

        Assertions.assertEquals(studentMapper.mapToDTO(student), studentService.save(student));
    }
}