package org.example.service;

import org.example.model.*;
import org.example.repository.GroupRepository;
import org.example.service.impl.GroupServiceImpl;
import org.example.servlet.mapper.ExamDTOMapper;
import org.example.servlet.mapper.GroupDTOMapper;
import org.example.servlet.mapper.StudentDTOMapper;
import org.example.servlet.mapper.SubjectDTOMapper;
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
    GroupEntity group;
    TeacherEntity teacher;
    SubjectEntity subject;

    GroupDTOMapper groupMapper = GroupDTOMapper.INSTANCE;
    StudentDTOMapper studentMapper = StudentDTOMapper.INSTANCE;
    SubjectDTOMapper subjectMapper = SubjectDTOMapper.INSTANCE;
    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockGroupRepository = Mockito.mock(GroupRepository.class);
        groupService = new GroupServiceImpl(mockGroupRepository);

        teacher = new TeacherEntity(1, "t", "t", "t");
        group = new GroupEntity(1, "П-0",
                new Date(new GregorianCalendar(2015, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2019, Calendar.JUNE, 30).getTimeInMillis()),
                teacher);
    }

    @Test
    void findByIdTest() throws SQLException {
        Mockito.when(mockGroupRepository.findById(1)).thenReturn(group);

        Assertions.assertEquals(groupService.findById(1), groupMapper.mapToDTO(group));

        group = new GroupEntity(90, "П-1",
                new Date(new GregorianCalendar(2015, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2019, Calendar.JUNE, 30).getTimeInMillis()),
                teacher);

        Assertions.assertNotEquals(groupService.findById(1), groupMapper.mapToDTO(group));
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
        teacher.setId(4);
        group = new GroupEntity(2, "Э-321",
                new Date(new GregorianCalendar(2010, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2020, Calendar.JUNE, 30).getTimeInMillis()),
                teacher);

        Mockito.when(mockGroupRepository.save(group)).thenReturn(group);

        group = new GroupEntity(2, "Э-321",
                new Date(new GregorianCalendar(2010, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2020, Calendar.JUNE, 30).getTimeInMillis()),
                teacher);

        Assertions.assertEquals(groupMapper.mapToDTO(group), groupService.save(group));
    }

    @Test
    void findAllTest() throws SQLException {
        List<GroupEntity> groupEntities = new ArrayList<>();
        groupEntities.add(group);

        Mockito.when(mockGroupRepository.findAll()).thenReturn(groupEntities);

        Assertions.assertEquals(groupService.findAll(), groupEntities.stream().map(groupMapper::mapToDTO).toList());
    }

    @Test
    void findAllStudentsWithGroupIdTest() throws SQLException {
        StudentEntity studentEntity = new StudentEntity(1, "Number 1", "One", null, group);
        List<StudentEntity> studentEntities = List.of(studentEntity);

        Mockito.when(mockGroupRepository.findAllStudentsWithGroupId(1)).thenReturn(studentEntities);

        Assertions.assertEquals(groupService.findAllStudentsWithGroupId(1), studentEntities.stream().map(studentMapper::mapToDTO).toList());
    }

    @Test
    void findAllSubjectsWithGroupIdTest() throws SQLException {
        SubjectEntity subjectEntity = new SubjectEntity(1, "TestS");
        List<SubjectEntity> subjectEntities = List.of(subjectEntity);

        Mockito.when(mockGroupRepository.findAllSubjectsWithGroupId(1)).thenReturn(subjectEntities);

        Assertions.assertEquals(groupService.findAllSubjectsWithGroupId(1), subjectEntities.stream().map(subjectMapper::mapToDTO).toList());
    }

    @Test
    void findAllExamsWithGroupIdTest() throws SQLException {
        teacher = new TeacherEntity(1, "t", "t", "t");
        subject = new SubjectEntity(1, "t");
        ExamEntity examEntity = new ExamEntity(1,
                new Date(new GregorianCalendar(2019, Calendar.MARCH, 6).getTimeInMillis()),
                group, subject, teacher);
        List<ExamEntity> examEntities = List.of(examEntity);

        Mockito.when(mockGroupRepository.findAllExamsWithGroupId(1)).thenReturn(examEntities);

        Assertions.assertEquals(groupService.findAllExamsWithGroupId(1), examEntities.stream().map(examMapper::mapToDTO).toList());
    }
}