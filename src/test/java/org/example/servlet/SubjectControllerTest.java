package org.example.servlet;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.service.GroupService;
import org.example.service.SubjectService;
import org.example.service.TeacherService;
import org.example.servlet.dto.SubjectDTO;
import org.example.servlet.mapper.*;
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
    void doGetTest() throws SQLException {
        Mockito.when(mockSubjectService.findById(1)).thenReturn(subjectMapper.mapToDTO(subjectEntity));
        subjectDTO = mockSubjectService.findById(1);

        subjectServlet.getSubject(1);

        subjectEntity = new SubjectEntity(1, "t");
        Assertions.assertEquals(subjectServlet.getSubject(1), subjectMapper.mapToDTO(subjectEntity));
    }

    @Test
    void doPostTest() throws SQLException {
        SubjectDTO dto = subjectMapper.mapToDTO(new SubjectEntity(0, "t"));
        Mockito.when(mockSubjectService.save(dto)).thenReturn(dto);

        subjectDTO = subjectServlet.createSubject(dto);

        Assertions.assertEquals(dto, subjectDTO);
    }

    @Test
    void doPutTest() throws SQLException {
        SubjectDTO dto = subjectMapper.mapToDTO(subjectEntity);

        subjectEntity = subjectMapper.mapToEntity(dto, 1);
        Mockito.when(mockSubjectService.save(dto, 1)).thenReturn(dto);
        subjectDTO = mockSubjectService.save(dto, 1);
        subjectServlet.updateSubject(1, subjectDTO);

        Assertions.assertEquals(dto, subjectDTO);
    }

    @Test
    void doDeleteTest() throws SQLException {
        subjectDTO = subjectMapper.mapToDTO(subjectEntity);
        Mockito.when(mockSubjectService.findById(1)).thenReturn(subjectDTO);

        String result = subjectServlet.deleteSubject(1);

        Assertions.assertEquals("Subject deleted!", result);

        subjectDTO = null;
        Mockito.when(mockSubjectService.findById(2)).thenReturn(subjectDTO);

        result = subjectServlet.deleteSubject(2);

        Assertions.assertEquals(result,  "There is no subject with id " + 2);
        TeacherEntity teacher = new TeacherEntity(1, "t", "t", "t");
        GroupEntity group = new GroupEntity(1,"t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacher);
        subjectEntity.setExams(Set.of(new ExamEntity(1, new Date(System.currentTimeMillis()), group, subjectEntity, teacher)));
        subjectDTO = subjectMapper.mapToDTO(subjectEntity);
        Mockito.when(mockSubjectService.findById(3)).thenReturn(subjectDTO);

        result = subjectServlet.deleteSubject(3);

        Assertions.assertEquals("Subject is connected to some groups and/or exams!",  result);
    }
}