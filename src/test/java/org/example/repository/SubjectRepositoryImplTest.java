package org.example.repository;

import org.example.db.ConnectionManager;
import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.repository.impl.GroupRepositoryImpl;
import org.example.repository.impl.SubjectRepositoryImpl;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.PostgreSQLContainer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class SubjectRepositoryImplTest {
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.4")
            .withInitScripts("db/AcademicPerformanceDb.sql", "db/InsertSQL.sql");

    static final Logger log = LoggerFactory.getLogger(SubjectRepositoryImplTest.class);
    Connection connection;

    SubjectRepository subjectRepository;

    @BeforeAll
    static void beforeAll() {
        container.start();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        ConnectionManager.setConfig(container.getJdbcUrl(), container.getUsername(), container.getPassword());

        subjectRepository = new SubjectRepositoryImpl();

        try {
            connection = ConnectionManager.getConnection();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try {
            connection.close();
            ConnectionManager.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Test
    void getConnectionTest() {
        Assertions.assertNotNull(connection);
        log.info("Connection is OK!");
    }

    @Test
    void getSubjectByIdTest() throws SQLException {
        SubjectEntity subject = subjectRepository.findById(1);
        Assertions.assertNotNull(subject);
        Assertions.assertFalse(subject.getTeachers().isEmpty());
        Assertions.assertFalse(subject.getExams().isEmpty());
        Assertions.assertFalse(subject.getGroups().isEmpty());

        subject = subjectRepository.findById(2);
        Assertions.assertNotNull(subject);

        Assertions.assertNull(subjectRepository.findById(50));
    }

    @Test
    void deleteSubjectByIdTest() throws SQLException {
        Assertions.assertTrue(subjectRepository.deleteById(3));
        Assertions.assertFalse(subjectRepository.deleteById(50));
    }

    @Test
    void saveSubjectTest() throws SQLException {
        SubjectEntity subject = subjectRepository.findById(2);
        subject.setName("Тест");

        subject = subjectRepository.save(subject);
        Assertions.assertEquals("Тест", subject.getName());

        subject = new SubjectEntity();
        subject.setName("2 Тест");

        subject = subjectRepository.save(subject);
        Assertions.assertEquals(4, subject.getId());
        Assertions.assertEquals("2 Тест", subject.getName());
    }
    @Autowired
    TeacherRepository teacherRepository;

    @Test
    void saveSubjectTeacherRelationshipTest() throws SQLException {
        SubjectEntity subject = subjectRepository.findById(2);
        TeacherEntity teacher = teacherRepository.findById(1);

        Assertions.assertEquals(teacher, subjectRepository.save(subject, teacher));

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM subject_teacher ORDER BY id DESC LIMIT 1");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        Assertions.assertEquals(3, resultSet.getInt("id"));
        Assertions.assertEquals(2, resultSet.getInt("subject_id"));
        Assertions.assertEquals(1, resultSet.getInt("teacher_id"));
    }

    @Test
    void saveSubjectGroupRelationshipTest() throws SQLException {
        GroupRepository groupRepository = new GroupRepositoryImpl();

        SubjectEntity subject = subjectRepository.findById(2);
        GroupEntity group = groupRepository.findById(1);

        Assertions.assertEquals(group, subjectRepository.save(subject, group));

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM subject_group ORDER BY id DESC LIMIT 1");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        Assertions.assertEquals(3, resultSet.getInt("id"));
        Assertions.assertEquals(2, resultSet.getInt("subject_id"));
        Assertions.assertEquals(1, resultSet.getInt("group_id"));
    }

    @Test
    void findAllSubjectsTest() throws SQLException {
        List<SubjectEntity> subjects = subjectRepository.findAll();

        Assertions.assertFalse(subjects.isEmpty());
    }

    @Test
    void findAllTeachersWithSubjectIdTest() throws SQLException {
        List<TeacherEntity> teachers = subjectRepository.findAllTeachersWithSubjectId(1);

        Assertions.assertFalse(teachers.isEmpty());
    }

    @Test
    void findAllExamsWithSubjectIdTest() throws SQLException {
        List<ExamEntity> exams = subjectRepository.findAllExamsWithSubjectId(1);

        Assertions.assertFalse(exams.isEmpty());
    }

    @Test
    void findAllGroupsWithSubjectIdTest() throws SQLException {
        List<GroupEntity> exams = subjectRepository.findAllGroupsWithSubjectId(1);

        Assertions.assertFalse(exams.isEmpty());
    }
}