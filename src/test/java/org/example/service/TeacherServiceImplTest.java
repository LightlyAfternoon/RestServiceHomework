package org.example.service;

import org.example.model.TeacherEntity;
import org.example.repository.TeacherRepository;
import org.example.repository.impl.TeacherRepositoryImpl;
import org.example.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class TeacherServiceImplTest {
    TeacherRepository mockTeacherRepository;
    TeacherService teacherService;
    TeacherEntity teacherEntity;

    @BeforeEach
    void setUp() {
        mockTeacherRepository = Mockito.mock(TeacherRepositoryImpl.class);
        teacherService = new TeacherServiceImpl(mockTeacherRepository);
        teacherEntity = new TeacherEntity(1, "t", "t", null);
    }

    @Test
    void findByIdTest() throws SQLException, IOException {
        try {
            Mockito.when(mockTeacherRepository.findById(1)).thenReturn(teacherEntity);

            Assertions.assertEquals(teacherService.findById(1), teacherEntity);
        } catch (SQLException e) {
            throw new SQLException(e);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Test
    void findAllTest() throws SQLException, IOException {
        List<TeacherEntity> teacherEntityList = new ArrayList<>();
        teacherEntityList.add(teacherEntity);

        try {
            Mockito.when(mockTeacherRepository.findAll()).thenReturn(teacherEntityList);

            Assertions.assertEquals(teacherService.findAll(), teacherEntityList);
        } catch (SQLException e) {
            throw new SQLException(e);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}