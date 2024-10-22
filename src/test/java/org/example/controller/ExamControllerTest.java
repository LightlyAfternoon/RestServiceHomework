package org.example.controller;

import org.example.controller.dto.ExamDTO;
import org.example.controller.mapper.GradeDTOMapper;
import org.example.model.*;
import org.example.service.ExamService;
import org.example.controller.mapper.ExamDTOMapper;
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

class ExamControllerTest {
    ExamController examController;
    ExamService mockExamService;
    ExamDTO examDTO;
    ExamEntity examEntity;
    TeacherEntity teacherEntity;
    GroupEntity groupEntity;
    SubjectEntity subjectEntity;

    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;
    GradeDTOMapper gradeMapper = GradeDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockExamService = Mockito.mock(ExamService.class);
        examController = new ExamController(mockExamService);

        teacherEntity = new TeacherEntity(1, "t", "t", "t");
        groupEntity = new GroupEntity(1, "t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacherEntity);
        subjectEntity = new SubjectEntity(1, "t");
        examEntity = new ExamEntity(1, new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                groupEntity, subjectEntity, teacherEntity);
    }

    @Test
    void getExamsTest() throws SQLException {
        Mockito.when(mockExamService.findAll()).thenReturn(Set.of(examMapper.mapToDTO(examEntity)));

        examEntity = new ExamEntity(1, new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                groupEntity, subjectEntity, teacherEntity);

        Assertions.assertEquals(examController.getExams(), Set.of(examMapper.mapToDTO(examEntity)));
    }

    @Test
    void getExamTest() throws SQLException {
        Mockito.when(mockExamService.findById(1)).thenReturn(examMapper.mapToDTO(examEntity));
        examDTO = mockExamService.findById(1);

        examEntity = new ExamEntity(1, new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                groupEntity, subjectEntity, teacherEntity);

        Assertions.assertEquals(examController.getExam(1), examMapper.mapToDTO(examEntity));
    }

    @Test
    void getGradesTest() throws SQLException {
        teacherEntity = new TeacherEntity(1, "t", "t", "t");
        subjectEntity = new SubjectEntity(1, "s");
        StudentEntity studentEntity = new StudentEntity(1, "s", "s", "s", groupEntity);
        GradeEntity gradeEntity = new GradeEntity(1, studentEntity, examEntity, (short) 3);
        Set<GradeEntity> gradeEntities = Set.of(gradeEntity);

        Mockito.when(mockExamService.findAllGradesWithExamId(1)).thenReturn(gradeEntities.stream().map(gradeMapper::mapToDTO).collect(Collectors.toSet()));

        gradeEntity = new GradeEntity(1, studentEntity, examEntity, (short) 3);
        gradeEntities = Set.of(gradeEntity);

        Assertions.assertEquals(examController.getGrades(1), gradeEntities.stream().map(gradeMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void createExamTest() throws SQLException {
        ExamDTO dto = examMapper.mapToDTO(new ExamEntity(0, new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                groupEntity, subjectEntity, teacherEntity));
        Mockito.when(mockExamService.save(dto)).thenReturn(dto);

        examEntity = examMapper.mapToEntity(dto);
        examDTO = examController.createExam(dto);

        Assertions.assertEquals(dto, examDTO);
        Assertions.assertEquals(examEntity, examMapper.mapToEntity(examDTO));
    }

    @Test
    void updateExamTest() throws SQLException {
        ExamDTO dto = examMapper.mapToDTO(examEntity);

        examEntity = examMapper.mapToEntity(dto, 1);
        Mockito.when(mockExamService.save(dto, 1)).thenReturn(dto);
        examDTO = examController.updateExam(1, dto);

        Assertions.assertEquals(dto, examDTO);
        Assertions.assertEquals(examEntity, examMapper.mapToEntity(examDTO, examDTO.getId()));
    }

    @Test
    void deleteExamTest() throws SQLException {
        examDTO = examMapper.mapToDTO(examEntity);
        Mockito.when(mockExamService.findById(1)).thenReturn(examDTO);

        String result = examController.deleteExam(1);

        Assertions.assertEquals("Failed to delete exam!", result);

        examEntity.setGrades(Set.of(new GradeEntity(1,
                new StudentEntity(1, "s", "s", "s", groupEntity),
                new ExamEntity(1, new Date(System.currentTimeMillis()), groupEntity,
                        new SubjectEntity(1, "t"),
                        new TeacherEntity(1, "t", "t", "t")), (short) 2)));
        examDTO = examMapper.mapToDTO(examEntity);
        Mockito.when(mockExamService.findById(1)).thenReturn(examDTO);

        result = examController.deleteExam(1);

        Assertions.assertEquals("Exam is connected to some grades!", result);

        examDTO = null;
        Mockito.when(mockExamService.findById(2)).thenReturn(examDTO);

        result = examController.deleteExam(2);

        Assertions.assertEquals(result, "There is no exam with id " + 2);
    }
}