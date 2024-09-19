package org.example.repository;

import org.example.db.ConnectionManager;
import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.repository.impl.TeacherRepositoryImpl;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

class TeacherRepositoryImplTest {
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.4")
            .withInitScripts("db/AcademicPerformanceDb.sql", "db/InsertSQL.sql");

    static final Logger log = LoggerFactory.getLogger(TeacherRepositoryImplTest.class);
    Connection connection;

    TeacherRepository teacherRepository;

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

        teacherRepository = new TeacherRepositoryImpl();

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

        Assertions.assertThrows(SQLException.class, () -> teacherRepository.findById(50));
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
    void findAllSubjectsWithTeacherIdTest() throws SQLException {
        List<SubjectEntity> subjects = teacherRepository.findAllSubjectsWithTeacherId(1);

        Assertions.assertFalse(subjects.isEmpty());
    }

    @Test
    void findAllGroupsWithTeacherIdTest() throws SQLException {
        List<GroupEntity> groups = teacherRepository.findAllGroupsWithTeacherId(1);

        Assertions.assertFalse(groups.isEmpty());
    }

    @Test
    void findAllExamsWithTeacherIdTest() throws SQLException {
        List<ExamEntity> exams = teacherRepository.findAllExamsWithTeacherId(1);

        Assertions.assertFalse(exams.isEmpty());
    }
}