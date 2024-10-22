package org.example.controller;

import org.example.controller.dto.GradeDTO;
import org.example.model.*;
import org.example.service.GradeService;
import org.example.controller.mapper.GradeDTOMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

class GradeControllerTest {
    GradeController gradeController;
    GradeService mockGradeService;
    GradeDTO gradeDTO;
    GradeEntity gradeEntity;
    TeacherEntity teacherEntity;
    GroupEntity groupEntity;
    SubjectEntity subjectEntity;
    StudentEntity studentEntity;
    ExamEntity examEntity;

    GradeDTOMapper gradeMapper = GradeDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockGradeService = Mockito.mock(GradeService.class);
        gradeController = new GradeController(mockGradeService);

        teacherEntity = new TeacherEntity(1, "t", "t", "t");
        groupEntity = new GroupEntity(1, "t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacherEntity);
        subjectEntity = new SubjectEntity(1, "t");
        studentEntity = new StudentEntity(2, "t", "t", "t", groupEntity);
        examEntity = new ExamEntity(1, new Date(System.currentTimeMillis()), groupEntity, subjectEntity, teacherEntity);
        gradeEntity = new GradeEntity(1, studentEntity, examEntity, (short) 1);
    }

    @Test
    void getGradesTest() throws SQLException {
        Mockito.when(mockGradeService.findAll()).thenReturn(Set.of(gradeMapper.mapToDTO(gradeEntity)));

        gradeEntity = new GradeEntity(1, studentEntity, examEntity, (short) 1);

        Assertions.assertEquals(gradeController.getGrades(), Set.of(gradeMapper.mapToDTO(gradeEntity)));
    }

    @Test
    void getGradeTest() throws SQLException {
        Mockito.when(mockGradeService.findById(1)).thenReturn(gradeMapper.mapToDTO(gradeEntity));
        gradeDTO = mockGradeService.findById(1);

        gradeEntity = new GradeEntity(1, studentEntity, examEntity, (short) 1);

        Assertions.assertEquals(gradeController.getGrade(1), gradeMapper.mapToDTO(gradeEntity));
    }

    @Test
    void createGradeTest() throws SQLException {
        GradeDTO dto = gradeMapper.mapToDTO(new GradeEntity(0, studentEntity, examEntity, (short) 2));
        Mockito.when(mockGradeService.save(dto)).thenReturn(dto);

        gradeEntity = gradeMapper.mapToEntity(dto);
        gradeDTO = gradeController.createGrade(dto);

        Assertions.assertEquals(dto, gradeDTO);
        Assertions.assertEquals(gradeEntity, gradeMapper.mapToEntity(gradeDTO));
    }

    @Test
    void updateGradeTest() throws SQLException {
        GradeDTO dto = gradeMapper.mapToDTO(gradeEntity);

        gradeEntity = gradeMapper.mapToEntity(dto, 1);
        Mockito.when(mockGradeService.save(dto, 1)).thenReturn(dto);
        gradeDTO = gradeController.updateGrade(1, dto);

        Assertions.assertEquals(dto, gradeDTO);
        Assertions.assertEquals(gradeEntity, gradeMapper.mapToEntity(gradeDTO, gradeDTO.getId()));
    }

    @Test
    void deleteGradeTest() throws SQLException {
        gradeDTO = gradeMapper.mapToDTO(gradeEntity);
        Mockito.when(mockGradeService.findById(1)).thenReturn(gradeDTO);

        String result = gradeController.deleteGrade(1);

        Assertions.assertEquals("Failed to delete grade!", result);

        gradeDTO = null;
        Mockito.when(mockGradeService.findById(2)).thenReturn(gradeDTO);

        result = gradeController.deleteGrade(2);

        Assertions.assertEquals(result, "There is no grade with id " + 2);
    }
}