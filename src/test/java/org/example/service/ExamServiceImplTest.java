package org.example.service;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
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
    ExamEntity exam;
    TeacherEntity teacher;
    GroupEntity group;
    SubjectEntity subject;

    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockExamRepository = Mockito.mock(ExamRepository.class);
        examService = new ExamServiceImpl(mockExamRepository);

        teacher = new TeacherEntity(1, "t", "t", "t");
        group = new GroupEntity(1, "t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacher);
        subject = new SubjectEntity(1, "t");
        exam = new ExamEntity(1,
                new Date(new GregorianCalendar(2000, Calendar.SEPTEMBER, 2).getTimeInMillis()),
                group, subject, teacher);
    }

    @Test
    void findByIdTest() throws SQLException {
        Mockito.when(mockExamRepository.findById(1)).thenReturn(exam);

        Assertions.assertEquals(examService.findById(1), examMapper.mapToDTO(exam));

        subject.setId(2);
        teacher.setId(4);
        exam = new ExamEntity(2,
                new Date(new GregorianCalendar(2000, Calendar.SEPTEMBER, 2).getTimeInMillis()),
                group, subject, teacher);

        Assertions.assertNotEquals(examService.findById(1), examMapper.mapToDTO(exam));
    }

    @Test
    void findAllTest() throws SQLException {
        Set<ExamEntity> examEntities = new HashSet<>();
        examEntities.add(exam);

        Mockito.when(mockExamRepository.findAll()).thenReturn(examEntities);

        Assertions.assertEquals(examService.findAll(), examEntities.stream().map(examMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void saveExamTest() throws SQLException {
        subject.setId(5);
        teacher.setId(2);
        exam = new ExamEntity(1,
                new Date(new GregorianCalendar(2003, Calendar.SEPTEMBER, 2).getTimeInMillis()),
                group, subject, teacher);

        Mockito.when(mockExamRepository.save(exam)).thenReturn(exam);

        exam = new ExamEntity(1,
                new Date(new GregorianCalendar(2003, Calendar.SEPTEMBER, 2).getTimeInMillis()),
                group, subject, teacher);

        Assertions.assertEquals(examMapper.mapToDTO(exam), examService.save(examMapper.mapToDTO(exam)));
    }
}