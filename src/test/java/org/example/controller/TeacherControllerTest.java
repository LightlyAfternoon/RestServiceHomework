package org.example.controller;

import org.example.model.*;
import org.example.service.TeacherService;
import org.example.controller.dto.TeacherDTO;
import org.example.controller.mapper.*;
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
    void getTeachersTest() throws SQLException {
        Mockito.when(mockTeacherService.findAll()).thenReturn(Set.of(teacherMapper.mapToDTO(teacherEntity)));

        teacherEntity = new TeacherEntity(1, "t", "t", "t");
        Assertions.assertEquals(teacherServlet.getTeachers(), Set.of(teacherMapper.mapToDTO(teacherEntity)));
    }

    @Test
    void getTeacherTest() throws SQLException {
        Mockito.when(mockTeacherService.findById(1)).thenReturn(teacherMapper.mapToDTO(teacherEntity));

        teacherEntity = new TeacherEntity(1, "t", "t", "t");
        Assertions.assertEquals(teacherServlet.getTeacher(1), teacherMapper.mapToDTO(teacherEntity));
    }

    @Test
    void createTeacherTest() throws SQLException {
        TeacherDTO dto = teacherMapper.mapToDTO(new TeacherEntity(0, "t", "t", null));
        Mockito.when(mockTeacherService.save(dto)).thenReturn(dto);

        teacherDTO = teacherServlet.createTeacher(dto);

        Assertions.assertEquals(dto, teacherDTO);
    }

    @Test
    void updateTeacherTest() throws SQLException {
        TeacherDTO dto = teacherMapper.mapToDTO(teacherEntity);

        teacherEntity = teacherMapper.mapToEntity(dto, 1);
        Mockito.when(mockTeacherService.save(dto, 1)).thenReturn(dto);

        teacherDTO = teacherServlet.updateTeacher(1, dto);

        Assertions.assertEquals(dto, teacherDTO);
    }

    @Test
    void deleteTeacherTest() throws SQLException {
        teacherDTO = teacherMapper.mapToDTO(teacherEntity);
        Mockito.when(mockTeacherService.findById(1)).thenReturn(teacherDTO);

        String result = teacherServlet.deleteTeacher(1);

        Assertions.assertEquals("Failed to delete teacher!", result);

        teacherEntity.setGroups(Set.of(new GroupEntity(1, "g",
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()),
                new TeacherEntity(1, "t", "t", "t"))));
        teacherDTO = teacherMapper.mapToDTO(teacherEntity);
        Mockito.when(mockTeacherService.findById(1)).thenReturn(teacherMapper.mapToDTO(teacherEntity));

        result = teacherServlet.deleteTeacher(1);

        Assertions.assertEquals("Teacher is connected to some groups and/or exams!", result);

        teacherDTO = null;
        Mockito.when(mockTeacherService.findById(2)).thenReturn(teacherDTO);

        result = teacherServlet.deleteTeacher(2);

        Assertions.assertEquals(result, "There is no teacher with id " + 2);
    }
}