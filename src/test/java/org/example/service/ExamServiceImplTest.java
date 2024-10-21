package org.example.service;

import org.example.controller.mapper.GradeDTOMapper;
import org.example.model.*;
import org.example.repository.ExamRepository;
import org.example.service.impl.ExamServiceImpl;
import org.example.controller.mapper.ExamDTOMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class ExamServiceImplTest {
    ExamRepository mockExamRepository;
    ExamService examService;
    ExamEntity examEntity;
    TeacherEntity teacherEntity;
    GroupEntity groupEntity;
    SubjectEntity subjectEntity;

    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;
    GradeDTOMapper gradeMapper = GradeDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockExamRepository = Mockito.mock(ExamRepository.class);
        examService = new ExamServiceImpl(mockExamRepository);

        teacherEntity = new TeacherEntity(1, "t", "t", "t");
        groupEntity = new GroupEntity(1, "t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacherEntity);
        subjectEntity = new SubjectEntity(1, "t");
        examEntity = new ExamEntity(1,
                new Date(new GregorianCalendar(2000, Calendar.SEPTEMBER, 2).getTimeInMillis()),
                groupEntity, subjectEntity, teacherEntity);
    }

    @Test
    void findByIdTest() throws SQLException {
        Mockito.when(mockExamRepository.findById(1)).thenReturn(examEntity);

        Assertions.assertEquals(examService.findById(1), examMapper.mapToDTO(examEntity));

        subjectEntity.setId(2);
        teacherEntity.setId(4);
        examEntity = new ExamEntity(2,
                new Date(new GregorianCalendar(2000, Calendar.SEPTEMBER, 2).getTimeInMillis()),
                groupEntity, subjectEntity, teacherEntity);

        Assertions.assertNotEquals(examService.findById(1), examMapper.mapToDTO(examEntity));
    }

    @Test
    void findAllTest() throws SQLException {
        Set<ExamEntity> examEntities = new HashSet<>();
        examEntities.add(examEntity);

        Mockito.when(mockExamRepository.findAll()).thenReturn(examEntities);

        Assertions.assertEquals(examService.findAll(), examEntities.stream().map(examMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void saveExamTest() throws SQLException {
        examService = Mockito.spy(new ExamServiceImpl(mockExamRepository));

        subjectEntity.setId(5);
        teacherEntity.setId(2);
        examEntity = new ExamEntity(1,
                new Date(new GregorianCalendar(2003, Calendar.SEPTEMBER, 2).getTimeInMillis()),
                groupEntity, subjectEntity, teacherEntity);

        Mockito.when(examService.save(examMapper.mapToDTO(examEntity))).thenReturn(examMapper.mapToDTO(examEntity));

        examEntity = new ExamEntity(1,
                new Date(new GregorianCalendar(2003, Calendar.SEPTEMBER, 2).getTimeInMillis()),
                groupEntity, subjectEntity, teacherEntity);

        Assertions.assertEquals(examMapper.mapToDTO(examEntity), examService.save(examMapper.mapToDTO(examEntity)));
    }

    @Test
    void findAllGradesWithExamIdTest() throws SQLException {
        teacherEntity = new TeacherEntity(1, "t", "t", "t");
        groupEntity = new GroupEntity(1, "t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacherEntity);
        subjectEntity = new SubjectEntity(1, "t");
        StudentEntity studentEntity = new StudentEntity(2, "t", "t", "t", groupEntity);
        examEntity = new ExamEntity(1, new Date(System.currentTimeMillis()), groupEntity, subjectEntity, teacherEntity);
        GradeEntity gradeEntity = new GradeEntity(1, studentEntity, examEntity, (short) 4);
        Set<GradeEntity> gradeEntities = Set.of(gradeEntity);
        examEntity.setGrades(gradeEntities);

        Mockito.when(mockExamRepository.findById(1)).thenReturn(examEntity);

        Assertions.assertEquals(examService.findAllGradesWithExamId(1), gradeEntities.stream().map(gradeMapper::mapToDTO).collect(Collectors.toSet()));
    }
}