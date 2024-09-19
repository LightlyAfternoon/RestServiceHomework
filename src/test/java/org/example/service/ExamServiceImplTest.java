package org.example.service;

import org.example.model.ExamEntity;
import org.example.repository.ExamRepository;
import org.example.repository.impl.ExamRepositoryImpl;
import org.example.service.impl.ExamServiceImpl;
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

class ExamServiceImplTest {
    ExamRepository mockExamRepository;
    ExamService examService;
    ExamEntity examEntity;

    @BeforeEach
    void setUp() {
        mockExamRepository = Mockito.mock(ExamRepositoryImpl.class);
        examService = new ExamServiceImpl(mockExamRepository);

        examEntity = new ExamEntity(1,
                new Date(new GregorianCalendar(2000, Calendar.SEPTEMBER, 2).getTimeInMillis()),
                1, 1);
    }

    @Test
    void findByIdTest() throws SQLException {
        Mockito.when(mockExamRepository.findById(1)).thenReturn(examEntity);

        Assertions.assertEquals(examService.findById(1), examEntity);

        examEntity = new ExamEntity(2,
                new Date(new GregorianCalendar(2000, Calendar.SEPTEMBER, 2).getTimeInMillis()),
                2, 4);

        Assertions.assertNotEquals(examService.findById(1), examEntity);
    }

    @Test
    void deleteByIdTest() throws SQLException {
        Mockito.when(mockExamRepository.deleteById(1)).thenReturn(true);
        Mockito.when(mockExamRepository.deleteById(2)).thenReturn(false);

        Assertions.assertTrue(examService.deleteById(1));
        Assertions.assertFalse(examService.deleteById(2));
    }

    @Test
    void findAllTest() throws SQLException {
        List<ExamEntity> examEntities = new ArrayList<>();
        examEntities.add(examEntity);

        Mockito.when(mockExamRepository.findAll()).thenReturn(examEntities);

        Assertions.assertEquals(examService.findAll(), examEntities);
    }

    @Test
    void saveExamTest() throws SQLException {
        examEntity = new ExamEntity(1,
                new Date(new GregorianCalendar(2003, Calendar.SEPTEMBER, 2).getTimeInMillis()),
                5, 2);

        Mockito.when(mockExamRepository.save(examEntity)).thenReturn(examEntity);

        examEntity = new ExamEntity(1,
                new Date(new GregorianCalendar(2003, Calendar.SEPTEMBER, 2).getTimeInMillis()),
                5, 2);

        Assertions.assertEquals(examEntity, examService.save(examEntity));
    }
}