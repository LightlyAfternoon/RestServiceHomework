package org.example.controller;

import org.example.controller.dto.GroupDTO;
import org.example.controller.mapper.StudentDTOMapper;
import org.example.controller.mapper.SubjectDTOMapper;
import org.example.model.*;
import org.example.service.GroupService;
import org.example.controller.mapper.GroupDTOMapper;
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

class GroupControllerTest {
    GroupController groupController;
    GroupService mockGroupService;

    GroupDTO groupDTO;
    GroupEntity groupEntity;
    TeacherEntity teacherEntity;

    GroupDTOMapper groupMapper = GroupDTOMapper.INSTANCE;
    StudentDTOMapper studentMapper = StudentDTOMapper.INSTANCE;
    SubjectDTOMapper subjectMapper = SubjectDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockGroupService = Mockito.mock(GroupService.class);
        groupController = new GroupController(mockGroupService);

        teacherEntity = new TeacherEntity(1, "t", "t", "t");
        groupEntity = new GroupEntity(1, "t", new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2033, Calendar.JULY, 3).getTimeInMillis()), teacherEntity);
    }

    @Test
    void getGroupsTest() throws SQLException {
        Mockito.when(mockGroupService.findAll()).thenReturn(Set.of(groupMapper.mapToDTO(groupEntity)));

        groupEntity = new GroupEntity(1, "t", new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2033, Calendar.JULY, 3).getTimeInMillis()), teacherEntity);

        Assertions.assertEquals(groupController.getGroups(), Set.of(groupMapper.mapToDTO(groupEntity)));
    }

    @Test
    void getGroupTest() throws SQLException {
        Mockito.when(mockGroupService.findById(1)).thenReturn(groupMapper.mapToDTO(groupEntity));
        groupDTO = mockGroupService.findById(1);

        groupEntity = new GroupEntity(1, "t", new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2033, Calendar.JULY, 3).getTimeInMillis()), teacherEntity);

        Assertions.assertEquals(groupController.getGroup(1), groupMapper.mapToDTO(groupEntity));
    }

    @Test
    void getStudentsTest() throws SQLException {
        StudentEntity studentEntity = new StudentEntity(1, "s", "s", "s", groupEntity);
        Set<StudentEntity> studentEntities = Set.of(studentEntity);

        Mockito.when(mockGroupService.findAllStudentsWithGroupId(1)).thenReturn(studentEntities.stream().map(studentMapper::mapToDTO).collect(Collectors.toSet()));

        studentEntity = new StudentEntity(1, "s", "s", "s", groupEntity);
        studentEntities = Set.of(studentEntity);

        Assertions.assertEquals(groupController.getStudents(1), studentEntities.stream().map(studentMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void getSubjectsTest() throws SQLException {
        SubjectEntity subjectEntity = new SubjectEntity(1, "s");
        Set<SubjectEntity> subjectEntities = Set.of(subjectEntity);

        Mockito.when(mockGroupService.findAllSubjectsWithGroupId(1)).thenReturn(subjectEntities.stream().map(subjectMapper::mapToDTO).collect(Collectors.toSet()));

        subjectEntity = new SubjectEntity(1, "s");
        subjectEntities = Set.of(subjectEntity);

        Assertions.assertEquals(subjectEntities.stream().map(subjectMapper::mapToDTO).collect(Collectors.toSet()), groupController.getSubjects(1));
    }

    @Test
    void createGroupTest() throws SQLException {
        groupEntity = new GroupEntity(0, "t", new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2033, Calendar.JULY, 3).getTimeInMillis()), teacherEntity);
        groupEntity.setSubjects(Set.of(new SubjectEntity(1, "s")));
        groupEntity.setStudents(Set.of(new StudentEntity(1, "s", "s", "s", groupEntity)));
        GroupDTO dto = groupMapper.mapToDTO(groupEntity);
        Mockito.when(mockGroupService.save(dto)).thenReturn(dto);

        groupEntity = groupMapper.mapToEntity(dto);
        groupDTO = groupController.createGroup(dto);

        Assertions.assertEquals(dto, groupDTO);
        Assertions.assertEquals(groupEntity, groupMapper.mapToEntity(groupDTO));
    }

    @Test
    void updateGroupTest() throws SQLException {
        GroupDTO dto = groupMapper.mapToDTO(groupEntity);

        groupEntity = groupMapper.mapToEntity(dto, 1);
        Mockito.when(mockGroupService.save(dto, 1)).thenReturn(dto);
        groupDTO = groupController.updateGroup(1, dto);

        Assertions.assertEquals(dto, groupDTO);
        Assertions.assertEquals(groupEntity, groupMapper.mapToEntity(groupDTO, groupDTO.getId()));
    }

    @Test
    void deleteGroupTest() throws SQLException {
        groupDTO = groupMapper.mapToDTO(groupEntity);
        Mockito.when(mockGroupService.findById(1)).thenReturn(groupDTO);

        String result = groupController.deleteGroup(1);

        Assertions.assertEquals("Failed to delete group!", result);

        groupEntity.setStudents(Set.of(new StudentEntity(1, "s", "s", "s", groupEntity)));
        groupDTO = groupMapper.mapToDTO(groupEntity);
        Mockito.when(mockGroupService.findById(1)).thenReturn(groupDTO);

        result = groupController.deleteGroup(1);

        Assertions.assertEquals("Group is connected to some students and/or exams!", result);

        groupDTO = null;
        Mockito.when(mockGroupService.findById(2)).thenReturn(groupDTO);

        result = groupController.deleteGroup(2);

        Assertions.assertEquals(result, "There is no group with id " + 2);
    }
}