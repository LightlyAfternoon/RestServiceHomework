package org.example;

import org.example.db.ConnectionManager;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.repository.SubjectRepository;
import org.example.repository.TeacherRepository;
import org.example.repository.impl.SubjectRepositoryImpl;
import org.example.repository.impl.TeacherRepositoryImpl;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

class SubjectRepositoryImplTest {
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.4")
            .withInitScripts("db/AcademicPerformanceDb.sql", "db/InsertSQL.sql");

    private static final Logger log = LoggerFactory.getLogger(SubjectRepositoryImplTest.class);
    Connection connection;

    @BeforeAll
    static void beforeAll() {
        container.start();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @BeforeEach
    void setUp() throws SQLException, IOException {
        ConnectionManager connectionManager = new ConnectionManager(container.getJdbcUrl(), container.getUsername(),container.getPassword());

        try {
            connection = connectionManager.getConnection();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try {
            connection.close();
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
    void getSubjectByIdTest() throws SQLException, IOException {
        SubjectRepository subjectRepository = new SubjectRepositoryImpl();

        SubjectEntity subject = subjectRepository.findById(1);
        Assertions.assertNotNull(subject);
        Assertions.assertFalse(subject.getTeachers().isEmpty());

        subject = subjectRepository.findById(2);
        Assertions.assertNotNull(subject);

        Assertions.assertThrows(SQLException.class, () -> subjectRepository.findById(50));
    }

    @Test
    void deleteSubjectByIdTest() throws SQLException, IOException {
        SubjectRepository subjectRepository = new SubjectRepositoryImpl();

        Assertions.assertTrue(subjectRepository.deleteById(3));
        Assertions.assertFalse(subjectRepository.deleteById(50));
    }

    @Test
    void saveSubjectTest() throws SQLException, IOException {
        SubjectRepository subjectRepository = new SubjectRepositoryImpl();

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

    @Test
    void saveSubjectTeacherConnectionTest() throws SQLException, IOException {
        SubjectRepository subjectRepository = new SubjectRepositoryImpl();
        TeacherRepository teacherRepository = new TeacherRepositoryImpl();

        SubjectEntity subject = subjectRepository.findById(2);
        TeacherEntity teacher = teacherRepository.findById(1);

        Assertions.assertTrue(subjectRepository.save(subject, teacher).contains(Map.entry(subject, teacher)));

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM subject_teacher ORDER BY id DESC LIMIT 1");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        Assertions.assertEquals(3, resultSet.getInt("id"));
        Assertions.assertEquals(2, resultSet.getInt("subject_id"));
        Assertions.assertEquals(1, resultSet.getInt("teacher_id"));
    }

    @Test
    void findAllSubjectsTest() throws SQLException, IOException {
        SubjectRepository subjectRepository = new SubjectRepositoryImpl();
        List<SubjectEntity> subjects = subjectRepository.findAll();

        Assertions.assertFalse(subjects.isEmpty());
    }

    @Test
    void findAllTeachersWithSubjectIdTest() throws SQLException, IOException {
        SubjectRepository subjectRepository = new SubjectRepositoryImpl();
        List<TeacherEntity> teachers = subjectRepository.findAllTeachersWithSubjectId(1);

        Assertions.assertFalse(teachers.isEmpty());
    }
}