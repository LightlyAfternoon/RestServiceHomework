package org.example.service;

import org.example.model.GradeEntity;
import org.example.repository.GradeRepository;
import org.example.repository.impl.GradeRepositoryImpl;
import org.example.service.impl.GradeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class GradeServiceImplTest {
    GradeRepository mockGradeRepository;
    GradeService gradeService;
    GradeEntity gradeEntity;

    @BeforeEach
    void setUp() {
        mockGradeRepository = Mockito.mock(GradeRepositoryImpl.class);
        gradeService = new GradeServiceImpl(mockGradeRepository);

        gradeEntity = new GradeEntity(1, 1, 1, (short) 4);
    }

    @Test
    void findByIdTest() throws SQLException, IOException {
        Mockito.when(mockGradeRepository.findById(1)).thenReturn(gradeEntity);

        Assertions.assertEquals(gradeService.findById(1), gradeEntity);

        gradeEntity = new GradeEntity(2, 2, 1, (short) 4);

        Assertions.assertNotEquals(gradeService.findById(1), gradeEntity);
    }

    @Test
    void deleteByIdTest() throws SQLException, IOException {
        Mockito.when(mockGradeRepository.deleteById(1)).thenReturn(true);
        Mockito.when(mockGradeRepository.deleteById(2)).thenReturn(false);

        Assertions.assertTrue(gradeService.deleteById(1));
        Assertions.assertFalse(gradeService.deleteById(2));
    }

    @Test
    void findAllTest() throws SQLException, IOException {
        List<GradeEntity> gradeEntities = new ArrayList<>();
        gradeEntities.add(gradeEntity);

        Mockito.when(mockGradeRepository.findAll()).thenReturn(gradeEntities);

        Assertions.assertEquals(gradeService.findAll(), gradeEntities);
    }

    @Test
    void saveGradeTest() throws SQLException, IOException {
        gradeEntity = new GradeEntity(3, 4, 3, (short) 2);

        Mockito.when(mockGradeRepository.save(gradeEntity)).thenReturn(gradeEntity);

        gradeEntity = new GradeEntity(2, 4, 3, (short) 2);

        Assertions.assertEquals(gradeEntity, gradeService.save(gradeEntity));
    }
}