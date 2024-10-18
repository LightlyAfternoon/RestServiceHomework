package org.example.servlet;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.service.TeacherService;
import org.example.servlet.dto.TeacherDTO;
import org.example.servlet.mapper.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

class TeacherControllerTest {
    TeacherController teacherServlet;
    TeacherService mockTeacherService;
    TeacherDTO teacherDTO;
    TeacherEntity teacherEntity;

    TeacherDTOMapper teacherMapper = TeacherDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockTeacherService = Mockito.mock(TeacherService.class);
        teacherServlet = new TeacherController(mockTeacherService);
        teacherEntity = new TeacherEntity(1, "t", "t", "t");
    }

    @Test
    void doGetTest() throws SQLException {
        Mockito.when(mockTeacherService.findById(1)).thenReturn(teacherMapper.mapToDTO(teacherEntity));
        teacherDTO = mockTeacherService.findById(1);

        teacherServlet.getTeacher(1);

        teacherEntity = new TeacherEntity(1, "t", "t", "t");
        Assertions.assertEquals(teacherServlet.getTeacher(1), teacherMapper.mapToDTO(teacherEntity));
    }

    @Test
    void doPostTest() throws SQLException {
        TeacherDTO dto = teacherMapper.mapToDTO(new TeacherEntity(0, "t", "t", null));
        Mockito.when(mockTeacherService.save(dto)).thenReturn(dto);

        teacherDTO = teacherServlet.createTeacher(dto);

        Assertions.assertEquals(dto, teacherDTO);
    }

    @Test
    void doPutTest() throws SQLException {
        TeacherDTO dto = teacherMapper.mapToDTO(teacherEntity);

        teacherEntity = teacherMapper.mapToEntity(dto, 1);
        Mockito.when(mockTeacherService.save(dto, 1)).thenReturn(dto);
        teacherDTO = mockTeacherService.save(dto, 1);
        teacherServlet.updateTeacher(1, teacherDTO);

        Assertions.assertEquals(dto, teacherDTO);
    }

    @Test
    void doDeleteTest() throws SQLException {
        teacherDTO = teacherMapper.mapToDTO(teacherEntity);
        Mockito.when(mockTeacherService.findById(1)).thenReturn(teacherDTO);

        String result = teacherServlet.deleteTeacher(1);

        Assertions.assertEquals("Teacher deleted!", result);

        teacherDTO = null;
        Mockito.when(mockTeacherService.findById(2)).thenReturn(teacherDTO);

        result = teacherServlet.deleteTeacher(2);

        Assertions.assertEquals(result,  "There is no teacher with id " + 2);

        teacherEntity.setExams(Set.of(new ExamEntity(1, new Date(System.currentTimeMillis()),
                new GroupEntity(1, "t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacherEntity),
                new SubjectEntity(1, "t"), teacherEntity)));
        teacherDTO = teacherMapper.mapToDTO(teacherEntity);
        Mockito.when(mockTeacherService.findById(3)).thenReturn(teacherDTO);

        result = teacherServlet.deleteTeacher(3);

        Assertions.assertEquals("Teacher is connected to some groups and/or exams!",  result);
    }
}