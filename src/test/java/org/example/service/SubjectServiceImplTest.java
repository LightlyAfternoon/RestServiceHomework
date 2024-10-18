package org.example.service;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.repository.SubjectRepository;
import org.example.service.impl.SubjectServiceImpl;
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
import java.util.HashSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.stream.Collectors;

class SubjectServiceImplTest {
    SubjectRepository mockSubjectRepository;
    SubjectService subjectService;
    SubjectEntity subjectEntity;

    SubjectDTOMapper subjectMapper = SubjectDTOMapper.INSTANCE;
    TeacherDTOMapper teacherMapper = TeacherDTOMapper.INSTANCE;
    GroupDTOMapper groupMapper = GroupDTOMapper.INSTANCE;
    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockSubjectRepository = Mockito.mock(SubjectRepository.class);
        subjectService = new SubjectServiceImpl(mockSubjectRepository);

        subjectEntity = new SubjectEntity(1, "TestS");
    }

    @Test
    void findByIdTest() throws SQLException {
        Mockito.when(mockSubjectRepository.findById(1)).thenReturn(subjectEntity);

        Assertions.assertEquals(subjectService.findById(1), subjectMapper.mapToDTO(subjectEntity));

        subjectEntity = new SubjectEntity(1, "TestS2");

        Assertions.assertNotEquals(subjectService.findById(1), subjectMapper.mapToDTO(subjectEntity));
    }

    @Test
    void deleteByIdTest() {
        Mockito.doNothing().when(mockSubjectRepository).deleteById(1);
        Mockito.doThrow(DataIntegrityViolationException.class).when(mockSubjectRepository).deleteById(2);

        mockSubjectRepository.deleteById(1);
        Assertions.assertThrows(DataIntegrityViolationException.class,() -> subjectService.deleteById(2));
    }

    @Test
    void findAllTest() throws SQLException {
        Set<SubjectEntity> subjectEntities = new HashSet<>();
        subjectEntities.add(subjectEntity);

        Mockito.when(mockSubjectRepository.findAll()).thenReturn(subjectEntities);

        Assertions.assertEquals(subjectService.findAll(), subjectEntities.stream().map(subjectMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void findAllTeachersWithSubjectIdTest() throws SQLException {
        TeacherEntity teacherEntity = new TeacherEntity(1, "Lizzi", "Testova", "Testovna");
        Set<TeacherEntity> teacherEntities = Set.of(teacherEntity);
        subjectEntity.setTeachers(teacherEntities);

        Mockito.when(mockSubjectRepository.findById(1)).thenReturn(subjectEntity);

        Assertions.assertEquals(subjectService.findAllTeachersWithSubjectId(1), teacherEntities.stream().map(teacherMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void findAllGroupsWithSubjectIdTest() throws SQLException {
        TeacherEntity teacher = new TeacherEntity(1, "t", "t", "t");
        GroupEntity groupEntity = new GroupEntity(1,
                "t-11",
                new Date(new GregorianCalendar(2017, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2021, Calendar.JUNE, 28).getTimeInMillis()),
                teacher);
        Set<GroupEntity> groupEntities = Set.of(groupEntity);
        subjectEntity.setGroups(groupEntities);

        Mockito.when(mockSubjectRepository.findById(1)).thenReturn(subjectEntity);

        Assertions.assertEquals(subjectService.findAllGroupsWithSubjectId(1), groupEntities.stream().map(groupMapper::mapToDTO).collect(Collectors.toSet()));

    }

    @Test
    void findAllExamsWithSubjectIdTest() throws SQLException {
        TeacherEntity teacher = new TeacherEntity(1, "t", "t", "t");
        GroupEntity group = new GroupEntity(1, "t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacher);
        SubjectEntity subject = new SubjectEntity(1, "t");
        ExamEntity examEntity = new ExamEntity(1,
                new Date(new GregorianCalendar(2019, Calendar.MARCH, 6).getTimeInMillis()),
                group, subject, teacher);
        Set<ExamEntity> examEntities = Set.of(examEntity);
        subjectEntity.setExams(examEntities);

        Mockito.when(mockSubjectRepository.findById(1)).thenReturn(subjectEntity);

        Assertions.assertEquals(subjectService.findAllExamsWithSubjectId(1), examEntities.stream().map(examMapper::mapToDTO).collect(Collectors.toSet()));
    }

    @Test
    void saveSubjectTest() throws SQLException {
        subjectEntity = new SubjectEntity(2, "S2");

        Mockito.when(mockSubjectRepository.save(subjectEntity)).thenReturn(subjectEntity);

        Assertions.assertEquals(subjectMapper.mapToDTO(subjectEntity), subjectService.save(subjectMapper.mapToDTO(subjectEntity), subjectEntity.getId()));
    }
}