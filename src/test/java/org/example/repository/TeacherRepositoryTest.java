package org.example.repository;

import org.example.config.MyWebConfig;
import org.example.db.ConnectionManager;
import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

class TeacherRepositoryTest {
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.4")
            .withInitScripts("db/AcademicPerformanceDb.sql", "db/InsertSQL.sql");

    static final Logger log = LoggerFactory.getLogger(TeacherRepositoryTest.class);
    Connection connection;

    @Autowired
    TeacherRepository teacherRepository;

    @BeforeAll
    static void beforeAll() {
        var a = new AnnotationConfigApplicationContext(MyWebConfig.class);
        container.start();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        ConnectionManager.setConfig(container.getJdbcUrl(), container.getUsername(), container.getPassword());

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
    void getTeacherByIdTest() throws SQLException {
        TeacherEntity teacher = teacherRepository.findById(1);
        Assertions.assertNotNull(teacher);
        Assertions.assertFalse(teacher.getSubjects().isEmpty());
        Assertions.assertFalse(teacher.getGroups().isEmpty());
        Assertions.assertFalse(teacher.getExams().isEmpty());

        teacher = teacherRepository.findById(2);
        Assertions.assertNotNull(teacher);

        Assertions.assertNull(teacherRepository.findById(50));
    }

    @Test
    void deleteTeacherByIdTest() throws SQLException {
        Assertions.assertTrue(teacherRepository.deleteById(3));
        Assertions.assertFalse(teacherRepository.deleteById(50));
    }

    @Test
    void saveTeacherTest() throws SQLException {
        TeacherEntity teacher = teacherRepository.findById(2);
        teacher.setFirstName("Тест");
        teacher.setPatronymic("Тестович");

        teacher = teacherRepository.save(teacher);
        Assertions.assertEquals("Тест", teacher.getFirstName());
        Assertions.assertEquals("Тестович", teacher.getPatronymic());

        teacher = new TeacherEntity();
        teacher.setFirstName("Новая");
        teacher.setLastName("Тестовна");

        teacher = teacherRepository.save(teacher);
        Assertions.assertEquals(4, teacher.getId());
        Assertions.assertEquals("Новая", teacher.getFirstName());
        Assertions.assertEquals("Тестовна", teacher.getLastName());
    }

    @Test
    void findAllTeachersTest() throws SQLException {
        List<TeacherEntity> teachers = teacherRepository.findAll();

        Assertions.assertFalse(teachers.isEmpty());
    }

    @Test
    void findAllSubjectsByIdTest() throws SQLException {
        List<SubjectEntity> subjects = teacherRepository.findAllSubjectsById(1);

        Assertions.assertFalse(subjects.isEmpty());
    }

    @Test
    void findAllGroupsByIdTest() throws SQLException {
        List<GroupEntity> groups = teacherRepository.findAllGroupsById(1);

        Assertions.assertFalse(groups.isEmpty());
    }

    @Test
    void findAllExamsByIdTest() throws SQLException {
        List<ExamEntity> exams = teacherRepository.findAllExamsById(1);

        Assertions.assertFalse(exams.isEmpty());
    }
}