package org.example.service;

import org.example.model.*;
import org.example.repository.GradeRepository;
import org.example.service.impl.GradeServiceImpl;
import org.example.controller.mapper.GradeDTOMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class GradeServiceImplTest {
    GradeRepository mockGradeRepository;
    GradeService gradeService;
    GradeEntity gradeEntity;
    TeacherEntity teacherEntity;
    GroupEntity groupEntity;
    SubjectEntity subjectEntity;
    StudentEntity studentEntity;
    ExamEntity examEntity;

    GradeDTOMapper gradeMapper = GradeDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockGradeRepository = Mockito.mock(GradeRepository.class);
        gradeService = new GradeServiceImpl(mockGradeRepository);

        teacherEntity = new TeacherEntity(1, "t", "t", "t");
        groupEntity = new GroupEntity(1, "t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacherEntity);
        subjectEntity = new SubjectEntity(1, "t");
        studentEntity = new StudentEntity(2, "t", "t", "t", groupEntity);
        examEntity = new ExamEntity(1, new Date(System.currentTimeMillis()), groupEntity, subjectEntity, teacherEntity);
        gradeEntity = new GradeEntity(1, studentEntity, examEntity, (short) 4);
    }

    @Test
    void findByIdTest() throws SQLException {
        Mockito.when(mockGradeRepository.findById(1)).thenReturn(gradeEntity);

        Assertions.assertEquals(gradeService.findById(1), gradeMapper.mapToDTO(gradeEntity));

        gradeEntity = new GradeEntity(2, studentEntity, examEntity, (short) 4);

        Assertions.assertNotEquals(gradeService.findById(1), gradeMapper.mapToDTO(gradeEntity));
    }

    @Test
    void findAllTest() throws SQLException {
        Set<GradeEntity> gradeEntities = new HashSet<>();
        gradeEntities.add(gradeEntity);

        Mockito.when(mockGradeRepository.findAll()).thenReturn(gradeEntities);

        Assertions.assertEquals(gradeService.findAll(), gradeEntities.stream().map(gradeMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void saveGradeTest() throws SQLException {
        gradeService = Mockito.spy(new GradeServiceImpl(mockGradeRepository));

        studentEntity.setId(4);
        examEntity.setId(3);
        gradeEntity = new GradeEntity(3, studentEntity, examEntity, (short) 2);

        Mockito.doReturn(gradeMapper.mapToDTO(gradeEntity)).when(gradeService).save(gradeMapper.mapToDTO(gradeEntity));

        gradeEntity = new GradeEntity(3, studentEntity, examEntity, (short) 2);

        Assertions.assertEquals(gradeMapper.mapToDTO(gradeEntity), gradeService.save(gradeMapper.mapToDTO(gradeEntity)));
    }
}