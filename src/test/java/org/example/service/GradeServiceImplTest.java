package org.example.service;

import org.example.model.*;
import org.example.repository.GradeRepository;
import org.example.service.impl.GradeServiceImpl;
import org.example.servlet.mapper.GradeDTOMapper;
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
    GradeEntity grade;
    TeacherEntity teacher;
    GroupEntity group;
    SubjectEntity subject;
    StudentEntity student;
    ExamEntity exam;

    GradeDTOMapper gradeMapper = GradeDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockGradeRepository = Mockito.mock(GradeRepository.class);
        gradeService = new GradeServiceImpl(mockGradeRepository);

        teacher = new TeacherEntity(1, "t", "t", "t");
        group = new GroupEntity(1, "t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacher);
        subject = new SubjectEntity(1, "t");
        student = new StudentEntity(2, "t", "t", "t", group);
        exam = new ExamEntity(1, new Date(System.currentTimeMillis()), group, subject, teacher);
        grade = new GradeEntity(1, student, exam, (short) 4);
    }

    @Test
    void findByIdTest() throws SQLException {
        Mockito.when(mockGradeRepository.findById(1)).thenReturn(grade);

        Assertions.assertEquals(gradeService.findById(1), gradeMapper.mapToDTO(grade));

        grade = new GradeEntity(2, student, exam, (short) 4);

        Assertions.assertNotEquals(gradeService.findById(1), gradeMapper.mapToDTO(grade));
    }

    @Test
    void deleteByIdTest() throws SQLException {
        Mockito.when(mockGradeRepository.deleteById(1)).thenReturn(true);
        Mockito.when(mockGradeRepository.deleteById(2)).thenReturn(false);

        Assertions.assertTrue(gradeService.deleteById(1));
        Assertions.assertFalse(gradeService.deleteById(2));
    }

    @Test
    void findAllTest() throws SQLException {
        Set<GradeEntity> gradeEntities = new HashSet<>();
        gradeEntities.add(grade);

        Mockito.when(mockGradeRepository.findAll()).thenReturn(gradeEntities);

        Assertions.assertEquals(gradeService.findAll(), gradeEntities.stream().map(gradeMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void saveGradeTest() throws SQLException {
        student.setId(4);
        exam.setId(3);
        grade = new GradeEntity(3, student, exam, (short) 2);

        Mockito.when(mockGradeRepository.save(grade)).thenReturn(grade);

        grade = new GradeEntity(3, student, exam, (short) 2);

        Assertions.assertEquals(gradeMapper.mapToDTO(grade), gradeService.save(gradeMapper.mapToDTO(grade)));
    }
}