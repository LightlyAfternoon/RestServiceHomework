package org.example.service;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.repository.SubjectRepository;
import org.example.repository.impl.SubjectRepositoryImpl;
import org.example.service.impl.SubjectServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

class SubjectServiceImplTest {
    SubjectRepository mockSubjectRepository;
    SubjectService subjectService;
    SubjectEntity subjectEntity;

    @BeforeEach
    void setUp() {
        mockSubjectRepository = Mockito.mock(SubjectRepositoryImpl.class);
        subjectService = new SubjectServiceImpl(mockSubjectRepository);

        subjectEntity = new SubjectEntity(1, "TestS");
    }

    @Test
    void findByIdTest() throws SQLException, IOException {
        Mockito.when(mockSubjectRepository.findById(1)).thenReturn(subjectEntity);

        subjectEntity = new SubjectEntity(1, "TestS");

        Assertions.assertEquals(subjectService.findById(1), subjectEntity);

        subjectEntity = new SubjectEntity(1, "TestS2");

        Assertions.assertNotEquals(subjectService.findById(1), subjectEntity);
    }

    @Test
    void deleteByIdTest() throws SQLException, IOException {
        Mockito.when(mockSubjectRepository.deleteById(1)).thenReturn(true);
        Mockito.when(mockSubjectRepository.deleteById(2)).thenReturn(false);

        Assertions.assertTrue(subjectService.deleteById(1));
        Assertions.assertFalse(subjectService.deleteById(2));
    }

    @Test
    void findAllTest() throws SQLException, IOException {
        List<SubjectEntity> subjectEntities = new ArrayList<>();
        subjectEntities.add(subjectEntity);

        Mockito.when(mockSubjectRepository.findAll()).thenReturn(subjectEntities);

        Assertions.assertEquals(subjectService.findAll(), subjectEntities);
    }

    @Test
    void findAllTeachersWithSubjectIdTest() throws SQLException, IOException {
        TeacherEntity teacherEntity = new TeacherEntity(1, "Lizzi", "Testova", "Testovna");
        List<TeacherEntity> teacherEntities = List.of(teacherEntity);

        Mockito.when(mockSubjectRepository.findAllTeachersWithSubjectId(1)).thenReturn(teacherEntities);

        Assertions.assertEquals(subjectService.findAllTeachersWithSubjectId(1), teacherEntities);
    }

    @Test
    void findAllGroupsWithSubjectIdTest() throws SQLException, IOException {
        GroupEntity groupEntity = new GroupEntity(1,
                "t-11",
                new Date(new GregorianCalendar(2017, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2021, Calendar.JUNE, 28).getTimeInMillis()),
                1);
        List<GroupEntity> groupEntities = List.of(groupEntity);

        Mockito.when(mockSubjectRepository.findAllGroupsWithSubjectId(1)).thenReturn(groupEntities);

        Assertions.assertEquals(subjectService.findAllGroupsWithSubjectId(1), groupEntities);
    }

    @Test
    void findAllExamsWithSubjectIdTest() throws SQLException, IOException {
        ExamEntity examEntity = new ExamEntity(1,
                new Date(new GregorianCalendar(2019, Calendar.MARCH, 6).getTimeInMillis()),
                1,
                1);
        List<ExamEntity> examEntities = List.of(examEntity);


        Mockito.when(mockSubjectRepository.findAllExamsWithSubjectId(1)).thenReturn(examEntities);

        Assertions.assertEquals(subjectService.findAllExamsWithSubjectId(1), examEntities);

    }

    @Test
    void saveSubjectTest() throws SQLException, IOException {
        subjectEntity = new SubjectEntity(2, "S2");

        Mockito.when(mockSubjectRepository.save(subjectEntity)).thenReturn(subjectEntity);

        Assertions.assertEquals(subjectEntity, subjectService.save(subjectEntity));
    }

    @Test
    void saveSubjectTeacherRelationshipTest() throws SQLException, IOException {
        TeacherEntity teacherEntity = new TeacherEntity(1,
                "Лора",
                "Тестова",
                "Викторовна");

        Mockito.when(mockSubjectRepository.save(subjectEntity, teacherEntity)).thenReturn(teacherEntity);

        Assertions.assertEquals(teacherEntity, subjectService.save(subjectEntity, teacherEntity));

    }

    @Test
    void saveSubjectGroupRelationshipTest() throws SQLException, IOException {
        GroupEntity groupEntity = new GroupEntity(1,
                "П-12",
                new Date(new GregorianCalendar(2012, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                null,
                2);

        Mockito.when(mockSubjectRepository.save(subjectEntity, groupEntity)).thenReturn(groupEntity);

        groupEntity = new GroupEntity(1,
                "П-12",
                new Date(new GregorianCalendar(2012, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                null,
                2);

        Assertions.assertEquals(groupEntity, subjectService.save(subjectEntity, groupEntity));

    }
}