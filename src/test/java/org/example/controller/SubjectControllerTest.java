package org.example.controller;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.service.GroupService;
import org.example.service.SubjectService;
import org.example.service.TeacherService;
import org.example.controller.dto.SubjectDTO;
import org.example.controller.mapper.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

class SubjectControllerTest {
    SubjectController subjectServlet;
    SubjectService mockSubjectService;
    TeacherService mockTeacherService;
    GroupService mockGroupService;
    
    SubjectDTO subjectDTO;
    SubjectEntity subjectEntity;

    SubjectDTOMapper subjectMapper = SubjectDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockSubjectService = Mockito.mock(SubjectService.class);
        mockTeacherService = Mockito.mock(TeacherService.class);
        mockGroupService = Mockito.mock(GroupService.class);
        subjectServlet = new SubjectController(mockSubjectService, mockTeacherService, mockGroupService);
        subjectEntity = new SubjectEntity(1, "t");
    }

    @Test
    void getSubjectsTest() throws SQLException {
        Mockito.when(mockSubjectService.findAll()).thenReturn(Set.of(subjectMapper.mapToDTO(subjectEntity)));

        subjectEntity = new SubjectEntity(1, "t");
        Assertions.assertEquals(subjectServlet.getSubjects(), Set.of(subjectMapper.mapToDTO(subjectEntity)));
    }

    @Test
    void getSubjectTest() throws SQLException {
        Mockito.when(mockSubjectService.findById(1)).thenReturn(subjectMapper.mapToDTO(subjectEntity));

        subjectEntity = new SubjectEntity(1, "t");
        Assertions.assertEquals(subjectServlet.getSubject(1), subjectMapper.mapToDTO(subjectEntity));
    }

    @Test
    void createSubjectTest() throws SQLException {
        SubjectDTO dto = subjectMapper.mapToDTO(new SubjectEntity(0, "t"));
        Mockito.when(mockSubjectService.save(dto)).thenReturn(dto);

        subjectDTO = subjectServlet.createSubject(dto);

        Assertions.assertEquals(dto, subjectDTO);
    }

    @Test
    void updateSubjectTest() throws SQLException {
        SubjectDTO dto = subjectMapper.mapToDTO(subjectEntity);

        subjectEntity = subjectMapper.mapToEntity(dto, 1);
        Mockito.when(mockSubjectService.save(dto, 1)).thenReturn(dto);
        subjectDTO = subjectServlet.updateSubject(1, dto);

        Assertions.assertEquals(dto, subjectDTO);
    }

    @Test
    void deleteSubjectTest() throws SQLException {
        subjectDTO = subjectMapper.mapToDTO(subjectEntity);
        Mockito.when(mockSubjectService.findById(1)).thenReturn(subjectDTO);

        String result = subjectServlet.deleteSubject(1);

        Assertions.assertEquals("Failed to delete subject!", result);

        subjectEntity.setGroups(Set.of(new GroupEntity(1, "g",
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()),
                new TeacherEntity(1, "t", "t", "t"))));
        subjectDTO = subjectMapper.mapToDTO(subjectEntity);
        Mockito.when(mockSubjectService.findById(1)).thenReturn(subjectMapper.mapToDTO(subjectEntity));

        result = subjectServlet.deleteSubject(1);

        Assertions.assertEquals("Subject is connected to some groups and/or exams!", result);

        subjectDTO = null;
        Mockito.when(mockSubjectService.findById(2)).thenReturn(subjectDTO);

        result = subjectServlet.deleteSubject(2);

        Assertions.assertEquals(result, "There is no subject with id " + 2);
    }
}