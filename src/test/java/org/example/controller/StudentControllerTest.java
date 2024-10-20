package org.example.controller;

import org.example.controller.mapper.GradeDTOMapper;
import org.example.model.*;
import org.example.service.StudentService;
import org.example.controller.dto.StudentDTO;
import org.example.controller.mapper.StudentDTOMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.stream.Collectors;

class StudentControllerTest {
    StudentController studentController;
    StudentService mockStudentService;

    StudentDTO studentDTO;
    StudentEntity studentEntity;
    GroupEntity groupEntity;

    StudentDTOMapper studentMapper = StudentDTOMapper.INSTANCE;
    GradeDTOMapper gradeMapper = GradeDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockStudentService = Mockito.mock(StudentService.class);
        studentController = new StudentController(mockStudentService);

        TeacherEntity teacherEntity = new TeacherEntity(1, "t", "t", "t");
        groupEntity = new GroupEntity(1, "П-0",
                new Date(new GregorianCalendar(2015, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2019, Calendar.JUNE, 30).getTimeInMillis()),
                teacherEntity);
        studentEntity = new StudentEntity(1, "t", "t", "t", groupEntity);
    }

    @Test
    void getStudentsTest() throws SQLException {
        Mockito.when(mockStudentService.findAll()).thenReturn(Set.of(studentMapper.mapToDTO(studentEntity)));

        studentEntity = new StudentEntity(1, "t", "t", "t", groupEntity);
        Assertions.assertEquals(studentController.getStudents(), Set.of(studentMapper.mapToDTO(studentEntity)));
    }

    @Test
    void getStudentTest() throws SQLException {
        Mockito.when(mockStudentService.findById(1)).thenReturn(studentMapper.mapToDTO(studentEntity));
        studentDTO = mockStudentService.findById(1);

        studentEntity = new StudentEntity(1, "t", "t", "t", groupEntity);
        Assertions.assertEquals(studentController.getStudent(1), studentMapper.mapToDTO(studentEntity));
    }

    @Test
    void getGradesTest() throws SQLException {
        TeacherEntity teacherEntity = new TeacherEntity(1, "t", "t", "t");
        SubjectEntity subjectEntity = new SubjectEntity(1, "s");
        ExamEntity examEntity = new ExamEntity(1, new Date(System.currentTimeMillis()), groupEntity, subjectEntity, teacherEntity);
        GradeEntity gradeEntity = new GradeEntity(1, studentEntity, examEntity, (short) 3);
        Set<GradeEntity> gradeEntities = Set.of(gradeEntity);

        Mockito.when(mockStudentService.findAllGradesWithServiceId(1)).thenReturn(gradeEntities.stream().map(gradeMapper::mapToDTO).collect(Collectors.toSet()));

        gradeEntity = new GradeEntity(1, studentEntity, examEntity, (short) 3);
        gradeEntities = Set.of(gradeEntity);

        Assertions.assertEquals(studentController.getGrades(1), gradeEntities.stream().map(gradeMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void createStudentTest() throws SQLException {
        StudentDTO dto = studentMapper.mapToDTO(new StudentEntity(0, "t", "t", "t", groupEntity));
        Mockito.when(mockStudentService.save(dto)).thenReturn(dto);

        studentDTO = studentController.createStudent(dto);

        Assertions.assertEquals(dto, studentDTO);
    }

    @Test
    void updateStudentTest() throws SQLException {
        StudentDTO dto = studentMapper.mapToDTO(studentEntity);

        studentEntity = studentMapper.mapToEntity(dto, 1);
        Mockito.when(mockStudentService.save(dto, 1)).thenReturn(dto);
        studentDTO = studentController.updateStudent(1, dto);

        Assertions.assertEquals(dto, studentDTO);
    }

    @Test
    void deleteStudentTest() throws SQLException {
        studentDTO = studentMapper.mapToDTO(studentEntity);
        Mockito.when(mockStudentService.findById(1)).thenReturn(studentDTO);

        String result = studentController.deleteStudent(1);

        Assertions.assertEquals("Failed to delete student!", result);

        studentEntity.setGrades(Set.of(new GradeEntity(1,
                new StudentEntity(1, "s", "s", "s", groupEntity),
                new ExamEntity(1, new Date(System.currentTimeMillis()), groupEntity,
                new SubjectEntity(1, "t"),
                new TeacherEntity(1, "t", "t", "t")), (short) 2)));
        studentDTO = studentMapper.mapToDTO(studentEntity);
        Mockito.when(mockStudentService.findById(1)).thenReturn(studentDTO);

        result = studentController.deleteStudent(1);

        Assertions.assertEquals("Student is connected to some grades!", result);

        studentDTO = null;
        Mockito.when(mockStudentService.findById(2)).thenReturn(studentDTO);

        result = studentController.deleteStudent(2);

        Assertions.assertEquals(result, "There is no student with id " + 2);
    }
}