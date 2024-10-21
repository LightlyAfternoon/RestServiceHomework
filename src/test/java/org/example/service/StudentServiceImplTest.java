package org.example.service;

import org.example.controller.mapper.GradeDTOMapper;
import org.example.model.*;
import org.example.repository.StudentRepository;
import org.example.service.impl.StudentServiceImpl;
import org.example.controller.mapper.StudentDTOMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.stream.Collectors;

class StudentServiceImplTest {
    StudentRepository mockStudentRepository;
    StudentService studentService;
    StudentEntity student;
    GroupEntity group;

    StudentDTOMapper studentMapper = StudentDTOMapper.INSTANCE;
    GradeDTOMapper gradeMapper = GradeDTOMapper.INSTANCE;

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
    void findAllTest() throws SQLException {
        Set<StudentEntity> studentEntities = new HashSet<>();
        studentEntities.add(student);

        Mockito.when(mockStudentRepository.findAll()).thenReturn(studentEntities);

        Assertions.assertEquals(studentService.findAll(), studentEntities.stream().map(studentMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void findAllGradesWithServiceId() throws SQLException {
        TeacherEntity teacherEntity = new TeacherEntity(1, "t", "t", "t");
        GroupEntity groupEntity = new GroupEntity(1,
                "t-11",
                new Date(new GregorianCalendar(2017, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2021, Calendar.JUNE, 28).getTimeInMillis()),
                teacherEntity);
        SubjectEntity subjectEntity = new SubjectEntity(1, "s");
        ExamEntity examEntity = new ExamEntity(1, new Date(System.currentTimeMillis()), groupEntity, subjectEntity, teacherEntity);
        GradeEntity gradeEntity = new GradeEntity(1, student, examEntity, (short) 3);
        Set<GradeEntity> gradeEntities = Set.of(gradeEntity);
        student.setGrades(gradeEntities);

        Mockito.when(mockStudentRepository.findById(1)).thenReturn(student);

        Assertions.assertEquals(studentService.findAllGradesWithServiceId(1), gradeEntities.stream().map(gradeMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void saveStudentTest() throws SQLException {
        studentService = Mockito.spy(new StudentServiceImpl(mockStudentRepository));

        student = new StudentEntity(2, "Клавдий", "Ломоносов", "Германович", group);

        Mockito.when(studentService.save(studentMapper.mapToDTO(student))).thenReturn(studentMapper.mapToDTO(student));

        student = new StudentEntity(2, "Клавдий", "Ломоносов", "Германович", group);

        Assertions.assertEquals(studentMapper.mapToDTO(student), studentService.save(studentMapper.mapToDTO(student)));
    }
}