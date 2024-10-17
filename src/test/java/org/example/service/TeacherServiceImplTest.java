package org.example.service;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.repository.TeacherRepository;
import org.example.service.impl.TeacherServiceImpl;
import org.example.servlet.mapper.ExamDTOMapper;
import org.example.servlet.mapper.GroupDTOMapper;
import org.example.servlet.mapper.SubjectDTOMapper;
import org.example.servlet.mapper.TeacherDTOMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

class TeacherServiceImplTest {
    TeacherRepository mockTeacherRepository;
    TeacherService teacherService;
    TeacherEntity teacherEntity;

    TeacherDTOMapper teacherMapper = TeacherDTOMapper.INSTANCE;
    GroupDTOMapper groupMapper = GroupDTOMapper.INSTANCE;
    SubjectDTOMapper subjectMapper = SubjectDTOMapper.INSTANCE;
    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockTeacherRepository = Mockito.mock(TeacherRepository.class);
        teacherService = new TeacherServiceImpl(mockTeacherRepository);

        teacherEntity = new TeacherEntity(1, "t", "t", null);
    }

    @Test
    void findByIdTest() throws SQLException {
        Mockito.when(mockTeacherRepository.findById(1)).thenReturn(teacherEntity);

        Assertions.assertEquals(teacherService.findById(1), teacherMapper.mapToDTO(teacherEntity));

        teacherEntity = new TeacherEntity(2, "t2", "t2", null);

        Assertions.assertNotEquals(teacherService.findById(1), teacherMapper.mapToDTO(teacherEntity));
    }

    @Test
    void deleteByIdTest() {
        Mockito.doNothing().when(mockTeacherRepository).deleteById(1);
        Mockito.doThrow(DataIntegrityViolationException.class).when(mockTeacherRepository).deleteById(2);

        mockTeacherRepository.deleteById(1);
        Assertions.assertThrows(DataIntegrityViolationException.class,() -> teacherService.deleteById(2));
    }

    @Test
    void findAllTest() throws SQLException {
        List<TeacherEntity> teacherEntities = new ArrayList<>();
        teacherEntities.add(teacherEntity);

        Mockito.when(mockTeacherRepository.findAll()).thenReturn(teacherEntities);

        Assertions.assertEquals(teacherService.findAll(), teacherEntities.stream().map(teacherMapper::mapToDTO).toList());
    }

    @Test
    void findAllGroupsWithTeacherIdTest() throws SQLException {
        GroupEntity groupEntity = new GroupEntity(1,
                "t-11",
                new Date(new GregorianCalendar(2017, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2021, Calendar.JUNE, 28).getTimeInMillis()),
                teacherEntity);
        List<GroupEntity> groupEntities = List.of(groupEntity);

        Mockito.when(mockTeacherRepository.findById(1).getGroups()).thenReturn(groupEntities);

        Assertions.assertEquals(teacherService.findAllGroupsWithTeacherId(1), groupEntities.stream().map(groupMapper::mapToDTO).toList());
    }

    @Test
    void findAllSubjectsWithTeacherIdTest() throws SQLException {
        SubjectEntity subjectEntity = new SubjectEntity(1, "TestS");
        List<SubjectEntity> subjectEntities = List.of(subjectEntity);

        Mockito.when(mockTeacherRepository.findById(1).getSubjects()).thenReturn(subjectEntities);

        Assertions.assertEquals(teacherService.findAllSubjectsWithTeacherId(1), subjectEntities.stream().map(subjectMapper::mapToDTO).toList());
    }

    @Test
    void findAllExamsWithTeacherIdTest() throws SQLException {
        TeacherEntity teacher = new TeacherEntity(1, "t", "t", "t");
        GroupEntity group = new GroupEntity(1, "t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacher);
        SubjectEntity subject = new SubjectEntity(1, "t");
        ExamEntity examEntity = new ExamEntity(1,
                new Date(new GregorianCalendar(2019, Calendar.MARCH, 6).getTimeInMillis()),
                group, subject, teacher);
        List<ExamEntity> examEntities = List.of(examEntity);

        Mockito.when(mockTeacherRepository.findById(1).getExams()).thenReturn(examEntities);

        Assertions.assertEquals(teacherService.findAllExamsWithTeacherId(1), examEntities.stream().map(examMapper::mapToDTO).toList());
    }

    @Test
    void saveTeacherTest() throws SQLException {
        teacherEntity = new TeacherEntity(2, "Тет", "Тт", "Ттт");

        Mockito.when(mockTeacherRepository.save(teacherEntity)).thenReturn(teacherEntity);

        teacherEntity = new TeacherEntity(2, "Тет", "Тт", "Ттт");

        Assertions.assertEquals(teacherMapper.mapToDTO(teacherEntity), teacherService.save(teacherMapper.mapToDTO(teacherEntity)));
    }
}