package org.example.service;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.repository.TeacherRepository;
import org.example.service.impl.TeacherServiceImpl;
import org.example.controller.mapper.ExamDTOMapper;
import org.example.controller.mapper.GroupDTOMapper;
import org.example.controller.mapper.SubjectDTOMapper;
import org.example.controller.mapper.TeacherDTOMapper;
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
    void findAllTest() throws SQLException {
        Set<TeacherEntity> teacherEntities = new HashSet<>();
        teacherEntities.add(teacherEntity);

        Mockito.when(mockTeacherRepository.findAll()).thenReturn(teacherEntities);

        Assertions.assertEquals(teacherService.findAll(), teacherEntities.stream().map(teacherMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void findAllGroupsWithTeacherIdTest() throws SQLException {
        TeacherEntity teacher = new TeacherEntity(1, "t", "t", "t");
        GroupEntity groupEntity = new GroupEntity(1,
                "t-11",
                new Date(new GregorianCalendar(2017, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2021, Calendar.JUNE, 28).getTimeInMillis()),
                teacherEntity);
        Set<GroupEntity> groupEntities = Set.of(groupEntity);
        teacher.setGroups(groupEntities);

        Mockito.when(mockTeacherRepository.findById(1)).thenReturn(teacher);

            Assertions.assertEquals(teacherService.findAllGroupsWithTeacherId(1), groupEntities.stream().map(groupMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void findAllSubjectsWithTeacherIdTest() throws SQLException {
        TeacherEntity teacher = new TeacherEntity(1, "t", "t", "t");
        SubjectEntity subjectEntity = new SubjectEntity(1, "TestS");
        Set<SubjectEntity> subjectEntities = Set.of(subjectEntity);
        teacher.setSubjects(subjectEntities);

        Mockito.when(mockTeacherRepository.findById(1)).thenReturn(teacher);

        Assertions.assertEquals(teacherService.findAllSubjectsWithTeacherId(1), subjectEntities.stream().map(subjectMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void findAllExamsWithTeacherIdTest() throws SQLException {
        TeacherEntity teacher = new TeacherEntity(1, "t", "t", "t");
        GroupEntity group = new GroupEntity(1, "t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacher);
        SubjectEntity subject = new SubjectEntity(1, "t");
        ExamEntity examEntity = new ExamEntity(1,
                new Date(new GregorianCalendar(2019, Calendar.MARCH, 6).getTimeInMillis()),
                group, subject, teacher);
        Set<ExamEntity> examEntities = Set.of(examEntity);
        teacher.setExams(examEntities);

        Mockito.when(mockTeacherRepository.findById(1)).thenReturn(teacher);

        Assertions.assertEquals(teacherService.findAllExamsWithTeacherId(1), examEntities.stream().map(examMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void saveTeacherTest() throws SQLException {
        teacherEntity = new TeacherEntity(2, "Тет", "Тт", "Ттт");

        Mockito.when(mockTeacherRepository.save(teacherEntity)).thenReturn(teacherEntity);

        teacherEntity = new TeacherEntity(2, "Тет", "Тт", "Ттт");

        Assertions.assertEquals(teacherMapper.mapToDTO(teacherEntity), teacherService.save(teacherMapper.mapToDTO(teacherEntity), teacherEntity.getId()));
    }
}