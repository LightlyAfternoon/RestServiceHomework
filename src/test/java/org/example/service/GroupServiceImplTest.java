package org.example.service;

import org.example.model.*;
import org.example.repository.GroupRepository;
import org.example.service.impl.GroupServiceImpl;
import org.example.controller.mapper.ExamDTOMapper;
import org.example.controller.mapper.GroupDTOMapper;
import org.example.controller.mapper.StudentDTOMapper;
import org.example.controller.mapper.SubjectDTOMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.stream.Collectors;

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
    void saveGroupTest() throws SQLException {
        groupService = Mockito.spy(new GroupServiceImpl(mockGroupRepository));

        group = new GroupEntity(2, "Э-321",
                new Date(new GregorianCalendar(2010, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2020, Calendar.JUNE, 30).getTimeInMillis()),
                teacher);

        Mockito.doReturn(groupMapper.mapToDTO(group)).when(groupService).save(groupMapper.mapToDTO(group));

        group = new GroupEntity(2, "Э-321",
                new Date(new GregorianCalendar(2010, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2020, Calendar.JUNE, 30).getTimeInMillis()),
                teacher);

        Assertions.assertEquals(groupMapper.mapToDTO(group), groupService.save(groupMapper.mapToDTO(group)));
    }

    @Test
    void findAllTest() throws SQLException {
        Set<GroupEntity> groupEntities = new HashSet<>();
        groupEntities.add(group);

        Mockito.when(mockGroupRepository.findAll()).thenReturn(groupEntities);

        Assertions.assertEquals(groupService.findAll(), groupEntities.stream().map(groupMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void findAllStudentsWithGroupIdTest() throws SQLException {
        StudentEntity studentEntity = new StudentEntity(1, "Number 1", "One", null, group);
        Set<StudentEntity> studentEntities = Set.of(studentEntity);
        group.setStudents(studentEntities);

        Mockito.when(mockGroupRepository.findById(1)).thenReturn(group);

        Assertions.assertEquals(groupService.findAllStudentsWithGroupId(1), studentEntities.stream().map(studentMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void findAllSubjectsWithGroupIdTest() throws SQLException {
        SubjectEntity subjectEntity = new SubjectEntity(1, "TestS");
        Set<SubjectEntity> subjectEntities = Set.of(subjectEntity);
        group.setSubjects(subjectEntities);

        Mockito.when(mockGroupRepository.findById(1)).thenReturn(group);

        Assertions.assertEquals(groupService.findAllSubjectsWithGroupId(1), subjectEntities.stream().map(subjectMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void findAllExamsWithGroupIdTest() throws SQLException {
        teacher = new TeacherEntity(1, "t", "t", "t");
        subject = new SubjectEntity(1, "t");
        ExamEntity examEntity = new ExamEntity(1,
                new Date(new GregorianCalendar(2019, Calendar.MARCH, 6).getTimeInMillis()),
                group, subject, teacher);
        Set<ExamEntity> examEntities = Set.of(examEntity);
        group.setExams(examEntities);

        Mockito.when(mockGroupRepository.findById(1)).thenReturn(group);

        Assertions.assertEquals(groupService.findAllExamsWithGroupId(1), examEntities.stream().map(examMapper::mapToDTO).collect(Collectors.toSet()));
    }
}