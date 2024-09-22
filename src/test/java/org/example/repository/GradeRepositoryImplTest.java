package org.example.repository;

import org.example.db.ConnectionManager;
import org.example.model.GradeEntity;
import org.example.repository.impl.GradeRepositoryImpl;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

class GradeRepositoryImplTest {
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.4")
            .withInitScripts("db/AcademicPerformanceDb.sql", "db/InsertSQL.sql");

    private static final Logger log = LoggerFactory.getLogger(GradeRepositoryImplTest.class);
    Connection connection;

    GradeRepository gradeRepository;

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

        gradeRepository = new GradeRepositoryImpl();

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
    void getGradeByIdTest() throws SQLException {
        GradeEntity grade = gradeRepository.findById(1);
        Assertions.assertNotNull(grade);

        grade = gradeRepository.findById(2);
        Assertions.assertNotNull(grade);

        Assertions.assertNull(gradeRepository.findById(50));
    }

    @Test
    void deleteGradeByIdTest() throws SQLException {
        Assertions.assertTrue(gradeRepository.deleteById(3));
        Assertions.assertFalse(gradeRepository.deleteById(50));
    }

    @Test
    void saveGradeTest() throws SQLException {
        GradeEntity grade = gradeRepository.findById(1);
        grade.setStudentId(2);
        grade.setExamId(1);
        grade.setMark((short) 3);

        grade = gradeRepository.save(grade);
        Assertions.assertEquals(2, grade.getStudentId());
        Assertions.assertEquals(1, grade.getExamId());
        Assertions.assertEquals(3, grade.getMark());

        grade = new GradeEntity();
        grade.setStudentId(3);
        grade.setExamId(3);
        grade.setMark((short) 2);

        grade = gradeRepository.save(grade);
        Assertions.assertEquals(3, grade.getStudentId());
        Assertions.assertEquals(3, grade.getExamId());
        Assertions.assertEquals(2, grade.getMark());
    }

    @Test
    void findAllGradesTest() throws SQLException {
        List<GradeEntity> grades = gradeRepository.findAll();

        Assertions.assertFalse(grades.isEmpty());
    }
}