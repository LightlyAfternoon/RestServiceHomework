package org.example.service;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.StudentEntity;
import org.example.model.SubjectEntity;
import org.example.repository.GroupRepository;
import org.example.repository.impl.GroupRepositoryImpl;
import org.example.service.impl.GroupServiceImpl;
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

class GroupServiceImplTest {
    GroupRepository mockGroupRepository;
    GroupService groupService;
    GroupEntity groupEntity;

    @BeforeEach
    void setUp() {
        mockGroupRepository = Mockito.mock(GroupRepositoryImpl.class);
        groupService = new GroupServiceImpl(mockGroupRepository);

        groupEntity = new GroupEntity(1, "П-0",
                new Date(new GregorianCalendar(2015, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2019, Calendar.JUNE, 30).getTimeInMillis()),
                1);
    }

    @Test
    void findByIdTest() throws SQLException {
        Mockito.when(mockGroupRepository.findById(1)).thenReturn(groupEntity);

        Assertions.assertEquals(groupService.findById(1), groupEntity);

        groupEntity = new GroupEntity(90, "П-1",
                new Date(new GregorianCalendar(2015, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2019, Calendar.JUNE, 30).getTimeInMillis()),
                1);

        Assertions.assertNotEquals(groupService.findById(1), groupEntity);
    }

    @Test
    void deleteByIdTest() throws SQLException {
        Mockito.when(mockGroupRepository.deleteById(1)).thenReturn(true);
        Mockito.when(mockGroupRepository.deleteById(2)).thenReturn(false);

        Assertions.assertTrue(groupService.deleteById(1));
        Assertions.assertFalse(groupService.deleteById(2));
    }

    @Test
    void saveGroupTest() throws SQLException {
        groupEntity = new GroupEntity(2, "Э-321",
                new Date(new GregorianCalendar(2010, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2020, Calendar.JUNE, 30).getTimeInMillis()),
                4);

        Mockito.when(mockGroupRepository.save(groupEntity)).thenReturn(groupEntity);

        groupEntity = new GroupEntity(2, "Э-321",
                new Date(new GregorianCalendar(2010, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2020, Calendar.JUNE, 30).getTimeInMillis()),
                4);

        Assertions.assertEquals(groupEntity, groupService.save(groupEntity));
    }

    @Test
    void findAllTest() throws SQLException {
        List<GroupEntity> groupEntities = new ArrayList<>();
        groupEntities.add(groupEntity);

        Mockito.when(mockGroupRepository.findAll()).thenReturn(groupEntities);

        Assertions.assertEquals(groupService.findAll(), groupEntities);
    }

    @Test
    void findAllStudentsWithGroupIdTest() throws SQLException {
        StudentEntity studentEntity = new StudentEntity(1, "Number 1", "One", null, 1);
        List<StudentEntity> studentEntities = List.of(studentEntity);

        Mockito.when(mockGroupRepository.findAllStudentsWithGroupId(1)).thenReturn(studentEntities);

        Assertions.assertEquals(groupService.findAllStudentsWithGroupId(1), studentEntities);
    }

    @Test
    void findAllSubjectsWithGroupIdTest() throws SQLException {
        SubjectEntity subjectEntity = new SubjectEntity(1, "TestS");
        List<SubjectEntity> subjectEntities = List.of(subjectEntity);

        Mockito.when(mockGroupRepository.findAllSubjectsWithGroupId(1)).thenReturn(subjectEntities);

        Assertions.assertEquals(groupService.findAllSubjectsWithGroupId(1), subjectEntities);
    }

    @Test
    void findAllExamsWithGroupIdTest() throws SQLException {
        ExamEntity examEntity = new ExamEntity(1,
                new Date(new GregorianCalendar(2019, Calendar.MARCH, 6).getTimeInMillis()),
                1,
                1);
        List<ExamEntity> examEntities = List.of(examEntity);

        Mockito.when(mockGroupRepository.findAllExamsWithGroupId(1)).thenReturn(examEntities);

        Assertions.assertEquals(groupService.findAllExamsWithGroupId(1), examEntities);
    }
}