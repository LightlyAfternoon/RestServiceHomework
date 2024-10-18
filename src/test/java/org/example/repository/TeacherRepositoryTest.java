package org.example.repository;

import org.example.config.MyTestConfig;
import org.example.db.ConnectionManager;
import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

class TeacherRepositoryTest {
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.4")
            .withInitScripts("db/AcademicPerformanceDb.sql", "db/InsertSQL.sql");

    static final Logger log = LoggerFactory.getLogger(TeacherRepositoryTest.class);
    Connection connection;
    AnnotationConfigApplicationContext context;

    TeacherRepository teacherRepository;

    @BeforeAll
    static void beforeAll() {
        container.start();
        ConnectionManager.setConfig(container.getJdbcUrl(), container.getUsername(), container.getPassword());
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        try {
            connection = ConnectionManager.getConnection();
            context = new AnnotationConfigApplicationContext(MyTestConfig.class);
            teacherRepository = context.getBean(TeacherRepository.class);
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
        TeacherEntity teacherEntity = teacherRepository.findById(1);
        log.info("---");

        Assertions.assertNotNull(teacherEntity);
        Assertions.assertFalse(teacherEntity.getSubjects().isEmpty());
        Assertions.assertFalse(teacherEntity.getGroups().isEmpty());
        Assertions.assertFalse(teacherEntity.getExams().isEmpty());

        teacherEntity = teacherRepository.findById(2);
        Assertions.assertNotNull(teacherEntity);

        log.info("---");
        Assertions.assertNull(teacherRepository.findById(50));
    }

    @Test
    void deleteTeacherByIdTest() throws SQLException {
        log.info("---");
        Assertions.assertNotNull(teacherRepository.findById(1));
        teacherRepository.deleteById(1);
        Assertions.assertNotNull(teacherRepository.findById(1));
        log.info("---");
        Assertions.assertNotNull(teacherRepository.findById(3));
        teacherRepository.deleteById(3);
        Assertions.assertNull(teacherRepository.findById(3));
        log.info("---");
        Assertions.assertNull(teacherRepository.findById(50));
        teacherRepository.deleteById(50);
        Assertions.assertNull(teacherRepository.findById(50));
    }

    @Test
    void saveTeacherTest() throws SQLException {
        TeacherEntity teacher = teacherRepository.findById(2);
        Assertions.assertNotNull(teacher);
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
        Set<TeacherEntity> teachers = teacherRepository.findAll();

        Assertions.assertFalse(teachers.isEmpty());
    }

    @Test
    void findAllSubjectsByIdTest() throws SQLException {
        Set<SubjectEntity> subjects = teacherRepository.findById(2).getSubjects();

        Assertions.assertFalse(subjects.isEmpty());
    }

    @Test
    void findAllGroupsByIdTest() throws SQLException {
        Set<GroupEntity> groups = teacherRepository.findById(2).getGroups();

        Assertions.assertFalse(groups.isEmpty());
    }

    @Test
    void findAllExamsByIdTest() throws SQLException {
        Set<ExamEntity> exams = teacherRepository.findById(2).getExams();

        Assertions.assertFalse(exams.isEmpty());
    }
}