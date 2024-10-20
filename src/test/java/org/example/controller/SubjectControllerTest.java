package org.example.controller;

import org.example.controller.mapper.ExamDTOMapper;
import org.example.controller.mapper.GroupDTOMapper;
import org.example.controller.mapper.TeacherDTOMapper;
import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.service.GroupService;
import org.example.service.SubjectService;
import org.example.service.TeacherService;
import org.example.controller.dto.SubjectDTO;
import org.example.controller.mapper.SubjectDTOMapper;
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

class SubjectControllerTest {
    SubjectController subjectController;
    SubjectService mockSubjectService;
    TeacherService mockTeacherService;
    GroupService mockGroupService;
    
    SubjectDTO subjectDTO;
    SubjectEntity subjectEntity;

    SubjectDTOMapper subjectMapper = SubjectDTOMapper.INSTANCE;
    TeacherDTOMapper teacherMapper = TeacherDTOMapper.INSTANCE;
    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;
    GroupDTOMapper groupMapper = GroupDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockSubjectService = Mockito.mock(SubjectService.class);
        mockTeacherService = Mockito.mock(TeacherService.class);
        mockGroupService = Mockito.mock(GroupService.class);
        subjectController = new SubjectController(mockSubjectService, mockTeacherService, mockGroupService);
        subjectEntity = new SubjectEntity(1, "t");
    }

    @Test
    void getSubjectsTest() throws SQLException {
        Mockito.when(mockSubjectService.findAll()).thenReturn(Set.of(subjectMapper.mapToDTO(subjectEntity)));

        subjectEntity = new SubjectEntity(1, "t");
        Assertions.assertEquals(subjectController.getSubjects(), Set.of(subjectMapper.mapToDTO(subjectEntity)));
    }

    @Test
    void getSubjectTest() throws SQLException {
        Mockito.when(mockSubjectService.findById(1)).thenReturn(subjectMapper.mapToDTO(subjectEntity));

        subjectEntity = new SubjectEntity(1, "t");
        Assertions.assertEquals(subjectController.getSubject(1), subjectMapper.mapToDTO(subjectEntity));
    }

    @Test
    void getTeachersTest() throws SQLException {
        TeacherEntity teacherEntity = new TeacherEntity(1, "t", "t", "t");
        Set<TeacherEntity> teacherEntities = Set.of(teacherEntity);

        Mockito.when(mockSubjectService.findAllTeachersWithSubjectId(1)).thenReturn(teacherEntities.stream().map(teacherMapper::mapToDTO).collect(Collectors.toSet()));

        teacherEntity = new TeacherEntity(1, "t", "t", "t");
        teacherEntities = Set.of(teacherEntity);

        Assertions.assertEquals(subjectController.getTeachers(1), teacherEntities.stream().map(teacherMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void getExamsTest() throws SQLException {
        TeacherEntity teacherEntity = new TeacherEntity(1, "t", "t", "t");
        GroupEntity groupEntity = new GroupEntity(1, "t", new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2033, Calendar.JULY, 3).getTimeInMillis()), teacherEntity);
        ExamEntity examEntity = new ExamEntity(1, new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                groupEntity, subjectEntity, teacherEntity);
        Set<ExamEntity> examEntities = Set.of(examEntity);

        Mockito.when(mockSubjectService.findAllExamsWithSubjectId(1)).thenReturn(examEntities.stream().map(examMapper::mapToDTO).collect(Collectors.toSet()));

        examEntity = new ExamEntity(1, new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                groupEntity, subjectEntity, teacherEntity);
        examEntities = Set.of(examEntity);

        Assertions.assertEquals(subjectController.getExams(1), examEntities.stream().map(examMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void getGroupsTest() throws SQLException {
        TeacherEntity teacherEntity = new TeacherEntity(1, "t", "t", "t");
        GroupEntity groupEntity = new GroupEntity(1, "t", new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2033, Calendar.JULY, 3).getTimeInMillis()), teacherEntity);
        Set<GroupEntity> groupEntities = Set.of(groupEntity);

        Mockito.when(mockSubjectService.findAllGroupsWithSubjectId(1)).thenReturn(groupEntities.stream().map(groupMapper::mapToDTO).collect(Collectors.toSet()));

        groupEntity = new GroupEntity(1, "t", new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2033, Calendar.JULY, 3).getTimeInMillis()), teacherEntity);
        groupEntities = Set.of(groupEntity);

        Assertions.assertEquals(groupEntities.stream().map(groupMapper::mapToDTO).collect(Collectors.toSet()), subjectController.getGroups(1));
    }

    @Test
    void createSubjectTest() throws SQLException {
        SubjectDTO dto = subjectMapper.mapToDTO(new SubjectEntity(0, "t"));
        Mockito.when(mockSubjectService.save(dto)).thenReturn(dto);

        subjectDTO = subjectController.createSubject(dto);

        Assertions.assertEquals(dto, subjectDTO);
    }

    @Test
    void updateSubjectTest() throws SQLException {
        SubjectDTO dto = subjectMapper.mapToDTO(subjectEntity);

        subjectEntity = subjectMapper.mapToEntity(dto, 1);
        Mockito.when(mockSubjectService.save(dto, 1)).thenReturn(dto);
        subjectDTO = subjectController.updateSubject(1, dto);

        Assertions.assertEquals(dto, subjectDTO);
    }

    @Test
    void deleteSubjectTest() throws SQLException {
        subjectDTO = subjectMapper.mapToDTO(subjectEntity);
        Mockito.when(mockSubjectService.findById(1)).thenReturn(subjectDTO);

        String result = subjectController.deleteSubject(1);

        Assertions.assertEquals("Failed to delete subject!", result);

        subjectEntity.setGroups(Set.of(new GroupEntity(1, "g",
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()),
                new TeacherEntity(1, "t", "t", "t"))));
        subjectDTO = subjectMapper.mapToDTO(subjectEntity);
        Mockito.when(mockSubjectService.findById(1)).thenReturn(subjectMapper.mapToDTO(subjectEntity));

        result = subjectController.deleteSubject(1);

        Assertions.assertEquals("Subject is connected to some groups and/or exams!", result);

        subjectDTO = null;
        Mockito.when(mockSubjectService.findById(2)).thenReturn(subjectDTO);

        result = subjectController.deleteSubject(2);

        Assertions.assertEquals(result, "There is no subject with id " + 2);
    }
}