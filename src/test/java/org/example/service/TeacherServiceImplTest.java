package org.example.service;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.repository.TeacherRepository;
import org.example.repository.impl.TeacherRepositoryImpl;
import org.example.service.impl.TeacherServiceImpl;
import org.example.servlet.mapper.TeacherDTOMapper;
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

class TeacherServiceImplTest {
    TeacherRepository mockTeacherRepository;
    TeacherService teacherService;
    TeacherEntity teacherEntity;

    TeacherDTOMapper teacherMapper = TeacherDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockTeacherRepository = Mockito.mock(TeacherRepositoryImpl.class);
        teacherService = new TeacherServiceImpl(mockTeacherRepository);

        teacherEntity = new TeacherEntity(1, "t", "t", null);
    }

    @Test
    void findByIdTest() throws SQLException {
        Mockito.when(mockTeacherRepository.findById(1)).thenReturn(teacherEntity);

        Assertions.assertEquals(teacherService.findById(1), teacherMapper.mapToDTO(teacherEntity));

        teacherEntity = new TeacherEntity(2, "t2", "t2", null);

        Assertions.assertNotEquals(teacherService.findById(1), teacherMapper.mapToDTO(teacherEntity));
    }

    @Test
    void deleteByIdTest() throws SQLException {
        Mockito.when(mockTeacherRepository.deleteById(1)).thenReturn(true);
        Mockito.when(mockTeacherRepository.deleteById(2)).thenReturn(false);

        Assertions.assertTrue(teacherService.deleteById(1));
        Assertions.assertFalse(teacherService.deleteById(2));
    }

    @Test
    void findAllTest() throws SQLException {
        List<TeacherEntity> teacherEntities = new ArrayList<>();
        teacherEntities.add(teacherEntity);

        Mockito.when(mockTeacherRepository.findAll()).thenReturn(teacherEntities);

        Assertions.assertEquals(teacherService.findAll(), teacherEntities);
    }

    @Test
    void findAllGroupsWithTeacherIdTest() throws SQLException {
        GroupEntity groupEntity = new GroupEntity(1,
                "t-11",
                new Date(new GregorianCalendar(2017, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2021, Calendar.JUNE, 28).getTimeInMillis()),
                1);
        List<GroupEntity> groupEntities = List.of(groupEntity);

        Mockito.when(mockTeacherRepository.findAllGroupsWithTeacherId(1)).thenReturn(groupEntities);

        Assertions.assertEquals(teacherService.findAllGroupsWithTeacherId(1), groupEntities);
    }

    @Test
    void findAllSubjectsWithTeacherIdTest() throws SQLException {
        SubjectEntity subjectEntity = new SubjectEntity(1, "TestS");
        List<SubjectEntity> subjectEntities = List.of(subjectEntity);

        Mockito.when(mockTeacherRepository.findAllSubjectsWithTeacherId(1)).thenReturn(subjectEntities);

        Assertions.assertEquals(teacherService.findAllSubjectsWithTeacherId(1), subjectEntities);
    }

    @Test
    void findAllExamsWithTeacherIdTest() throws SQLException {
        ExamEntity examEntity = new ExamEntity(1,
                new Date(new GregorianCalendar(2019, Calendar.MARCH, 6).getTimeInMillis()),
                1,
                1);
        List<ExamEntity> examEntities = List.of(examEntity);

        Mockito.when(mockTeacherRepository.findAllExamsWithTeacherId(1)).thenReturn(examEntities);

        Assertions.assertEquals(teacherService.findAllExamsWithTeacherId(1), examEntities);
    }

    @Test
    void saveTeacherTest() throws SQLException {
        teacherEntity = new TeacherEntity(2, "Тет", "Тт", "Ттт");

        Mockito.when(mockTeacherRepository.save(teacherEntity)).thenReturn(teacherEntity);

        teacherEntity = new TeacherEntity(2, "Тет", "Тт", "Ттт");

        Assertions.assertEquals(teacherMapper.mapToDTO(teacherEntity), teacherService.save(teacherEntity));
    }
}