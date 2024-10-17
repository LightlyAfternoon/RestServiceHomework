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

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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

//    @Test
//    void deleteByIdTest() throws SQLException {
//        Mockito.when(mockSubjectRepository.deleteById(1)).thenReturn(true);
//        Mockito.when(mockSubjectRepository.deleteById(2)).thenReturn(false);
//
//        Assertions.assertTrue(subjectService.deleteById(1));
//        Assertions.assertFalse(subjectService.deleteById(2));
//    }

    @Test
    void findAllTest() throws SQLException {
        List<SubjectEntity> subjectEntities = new ArrayList<>();
        subjectEntities.add(subjectEntity);

        Mockito.when(mockSubjectRepository.findAll()).thenReturn(subjectEntities);

        Assertions.assertEquals(subjectService.findAll(), subjectEntities.stream().map(subjectMapper::mapToDTO).toList());
    }

    @Test
    void findAllTeachersWithSubjectIdTest() throws SQLException {
        TeacherEntity teacherEntity = new TeacherEntity(1, "Lizzi", "Testova", "Testovna");
        List<TeacherEntity> teacherEntities = List.of(teacherEntity);

        Mockito.when(mockSubjectRepository.findAllTeachersWithSubjectId(1)).thenReturn(teacherEntities);

        Assertions.assertEquals(subjectService.findAllTeachersWithSubjectId(1), teacherEntities.stream().map(teacherMapper::mapToDTO).toList());
    }

    @Test
    void findAllGroupsWithSubjectIdTest() throws SQLException {
        TeacherEntity teacher = new TeacherEntity(1, "t", "t", "t");
        GroupEntity groupEntity = new GroupEntity(1,
                "t-11",
                new Date(new GregorianCalendar(2017, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2021, Calendar.JUNE, 28).getTimeInMillis()),
                teacher);
        List<GroupEntity> groupEntities = List.of(groupEntity);

        Mockito.when(mockSubjectRepository.findAllGroupsWithSubjectId(1)).thenReturn(groupEntities);

        Assertions.assertEquals(subjectService.findAllGroupsWithSubjectId(1), groupEntities.stream().map(groupMapper::mapToDTO).toList());
    }

    @Test
    void findAllExamsWithSubjectIdTest() throws SQLException {
        TeacherEntity teacher = new TeacherEntity(1, "t", "t", "t");
        GroupEntity group = new GroupEntity(1, "t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacher);
        SubjectEntity subject = new SubjectEntity(1, "t");
        ExamEntity examEntity = new ExamEntity(1,
                new Date(new GregorianCalendar(2019, Calendar.MARCH, 6).getTimeInMillis()),
                group, subject, teacher);
        List<ExamEntity> examEntities = List.of(examEntity);


        Mockito.when(mockSubjectRepository.findAllExamsWithSubjectId(1)).thenReturn(examEntities);

        Assertions.assertEquals(subjectService.findAllExamsWithSubjectId(1), examEntities.stream().map(examMapper::mapToDTO).toList());

    }

    @Test
    void saveSubjectTest() throws SQLException {
        subjectEntity = new SubjectEntity(2, "S2");

        Mockito.when(mockSubjectRepository.save(subjectEntity)).thenReturn(subjectEntity);

        Assertions.assertEquals(subjectMapper.mapToDTO(subjectEntity), subjectService.save(subjectEntity));
    }

    @Test
    void saveSubjectTeacherRelationshipTest() throws SQLException {
        TeacherEntity teacherEntity = new TeacherEntity(1,
                "Лора",
                "Тестова",
                "Викторовна");

        Mockito.when(mockSubjectRepository.save(subjectEntity, teacherEntity)).thenReturn(teacherEntity);

        Assertions.assertEquals(teacherMapper.mapToDTO(teacherEntity), subjectService.save(subjectEntity, teacherEntity));

    }

    @Test
    void saveSubjectGroupRelationshipTest() throws SQLException {
        TeacherEntity teacher = new TeacherEntity(2, "t", "t", "t");
        GroupEntity groupEntity = new GroupEntity(1,
                "П-12",
                new Date(new GregorianCalendar(2012, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                null,
                teacher);

        Mockito.when(mockSubjectRepository.save(subjectEntity, groupEntity)).thenReturn(groupEntity);

        groupEntity = new GroupEntity(1,
                "П-12",
                new Date(new GregorianCalendar(2012, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                null,
                teacher);

        Assertions.assertEquals(groupMapper.mapToDTO(groupEntity), subjectService.save(subjectEntity, groupEntity));

    }
}