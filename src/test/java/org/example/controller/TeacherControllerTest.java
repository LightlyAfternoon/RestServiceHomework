package org.example.controller;

import org.example.controller.mapper.ExamDTOMapper;
import org.example.controller.mapper.GroupDTOMapper;
import org.example.controller.mapper.SubjectDTOMapper;
import org.example.model.*;
import org.example.service.TeacherService;
import org.example.controller.dto.TeacherDTO;
import org.example.controller.mapper.TeacherDTOMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.stream.Collectors;

class TeacherControllerTest {
    TeacherController teacherController;
    TeacherService mockTeacherService;

    TeacherDTO teacherDTO;
    TeacherEntity teacherEntity;

    TeacherDTOMapper teacherMapper = TeacherDTOMapper.INSTANCE;
    GroupDTOMapper groupMapper = GroupDTOMapper.INSTANCE;
    SubjectDTOMapper subjectMapper = SubjectDTOMapper.INSTANCE;
    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockTeacherService = Mockito.mock(TeacherService.class);
        teacherController = new TeacherController(mockTeacherService);
        teacherEntity = new TeacherEntity(1, "t", "t", "t");
    }

    @Test
    void getTeachersTest() throws SQLException {
        Mockito.when(mockTeacherService.findAll()).thenReturn(Set.of(teacherMapper.mapToDTO(teacherEntity)));

        teacherEntity = new TeacherEntity(1, "t", "t", "t");
        Assertions.assertEquals(teacherController.getTeachers(), Set.of(teacherMapper.mapToDTO(teacherEntity)));
    }

    @Test
    void getTeacherTest() throws SQLException {
        Mockito.when(mockTeacherService.findById(1)).thenReturn(teacherMapper.mapToDTO(teacherEntity));

        teacherEntity = new TeacherEntity(1, "t", "t", "t");
        Assertions.assertEquals(teacherController.getTeacher(1), teacherMapper.mapToDTO(teacherEntity));
    }

    @Test
    void getGroupsTest() throws SQLException {
        GroupEntity groupEntity = new GroupEntity(1, "t", new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2033, Calendar.JULY, 3).getTimeInMillis()), teacherEntity);
        Set<GroupEntity> groupEntities = Set.of(groupEntity);

        Mockito.when(mockTeacherService.findAllGroupsWithTeacherId(1)).thenReturn(groupEntities.stream().map(groupMapper::mapToDTO).collect(Collectors.toSet()));

        groupEntity = new GroupEntity(1, "t", new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2033, Calendar.JULY, 3).getTimeInMillis()), teacherEntity);
        groupEntities = Set.of(groupEntity);

        Assertions.assertEquals(teacherController.getGroups(1), groupEntities.stream().map(groupMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void getSubjectsTest() throws SQLException {
        SubjectEntity subjectEntity = new SubjectEntity(1, "s");
        Set<SubjectEntity> subjectEntities = Set.of(subjectEntity);

        Mockito.when(mockTeacherService.findAllSubjectsWithTeacherId(1)).thenReturn(subjectEntities.stream().map(subjectMapper::mapToDTO).collect(Collectors.toSet()));

        subjectEntity = new SubjectEntity(1, "s");
        subjectEntities = Set.of(subjectEntity);

        Assertions.assertEquals(teacherController.getSubjects(1), subjectEntities.stream().map(subjectMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void getExamsTest() throws SQLException {
        GroupEntity groupEntity = new GroupEntity(1, "t", new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2033, Calendar.JULY, 3).getTimeInMillis()), teacherEntity);
        SubjectEntity subjectEntity = new SubjectEntity(1, "s");
        ExamEntity examEntity = new ExamEntity(1, new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                groupEntity, subjectEntity, teacherEntity);
        Set<ExamEntity> examEntities = Set.of(examEntity);

        Mockito.when(mockTeacherService.findAllExamsWithTeacherId(1)).thenReturn(examEntities.stream().map(examMapper::mapToDTO).collect(Collectors.toSet()));

        examEntity = new ExamEntity(1, new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                groupEntity, subjectEntity, teacherEntity);
        examEntities = Set.of(examEntity);

        Assertions.assertEquals(teacherController.getExams(1), examEntities.stream().map(examMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void createTeacherTest() throws SQLException {
        TeacherDTO dto = teacherMapper.mapToDTO(new TeacherEntity(0, "t", "t", null));
        Mockito.when(mockTeacherService.save(dto)).thenReturn(dto);

        teacherDTO = teacherController.createTeacher(dto);

        Assertions.assertEquals(dto, teacherDTO);
    }

    @Test
    void updateTeacherTest() throws SQLException {
        TeacherDTO dto = teacherMapper.mapToDTO(teacherEntity);

        teacherEntity = teacherMapper.mapToEntity(dto, 1);
        Mockito.when(mockTeacherService.save(dto, 1)).thenReturn(dto);

        teacherDTO = teacherController.updateTeacher(1, dto);

        Assertions.assertEquals(dto, teacherDTO);
    }

    @Test
    void deleteTeacherTest() throws SQLException {
        teacherDTO = teacherMapper.mapToDTO(teacherEntity);
        Mockito.when(mockTeacherService.findById(1)).thenReturn(teacherDTO);

        String result = teacherController.deleteTeacher(1);

        Assertions.assertEquals("Failed to delete teacher!", result);

        teacherEntity.setGroups(Set.of(new GroupEntity(1, "g",
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()),
                new TeacherEntity(1, "t", "t", "t"))));
        teacherDTO = teacherMapper.mapToDTO(teacherEntity);
        Mockito.when(mockTeacherService.findById(1)).thenReturn(teacherMapper.mapToDTO(teacherEntity));

        result = teacherController.deleteTeacher(1);

        Assertions.assertEquals("Teacher is connected to some groups and/or exams!", result);

        teacherDTO = null;
        Mockito.when(mockTeacherService.findById(2)).thenReturn(teacherDTO);

        result = teacherController.deleteTeacher(2);

        Assertions.assertEquals(result, "There is no teacher with id " + 2);
    }
}