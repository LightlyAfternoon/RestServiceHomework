package org.example.repository;

import org.example.db.ConnectionManager;
import org.example.model.ExamEntity;
import org.example.repository.impl.ExamRepositoryImpl;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

class ExamRepositoryImplTest {
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.4")
            .withInitScripts("db/AcademicPerformanceDb.sql", "db/InsertSQL.sql");
    
    private static final Logger log = LoggerFactory.getLogger(ExamRepositoryImplTest.class);
    Connection connection;

    ExamRepository examRepository;
    
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

        examRepository = new ExamRepositoryImpl();

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
    void getExamByIdTest() throws SQLException {
        ExamEntity exam = examRepository.findById(1);
        Assertions.assertNotNull(exam);

        exam = examRepository.findById(2);
        Assertions.assertNotNull(exam);

        Assertions.assertThrows(SQLException.class, () -> examRepository.findById(50));
    }

    @Test
    void deleteExamByIdTest() throws SQLException {
        Assertions.assertTrue(examRepository.deleteById(3));
        Assertions.assertFalse(examRepository.deleteById(50));
    }

    @Test
    void saveExamTest() throws SQLException {
        Calendar calendar = new GregorianCalendar(2023, Calendar.MAY, 26);
        Date startDate = new Date(calendar.getTimeInMillis());

        ExamEntity exam = examRepository.findById(2);
        exam.setStartDate(startDate);
        exam.setGroupId(2);
        exam.setSubjectTeacherId(2);

        exam = examRepository.save(exam);
        Assertions.assertEquals(startDate, exam.getStartDate());
        Assertions.assertEquals(2, exam.getGroupId());
        Assertions.assertEquals(2, exam.getSubjectTeacherId());

        exam = new ExamEntity();
        calendar.add(Calendar.MONTH, 8);
        startDate = new Date(calendar.getTimeInMillis());
        exam.setStartDate(startDate);
        exam.setGroupId(2);
        exam.setSubjectTeacherId(1);

        exam = examRepository.save(exam);
        Assertions.assertEquals(startDate, exam.getStartDate());
        Assertions.assertEquals(2, exam.getGroupId());
        Assertions.assertEquals(1, exam.getSubjectTeacherId());
    }

    @Test
    void findAllExamsTest() throws SQLException {
        List<ExamEntity> exams = examRepository.findAll();

        Assertions.assertFalse(exams.isEmpty());
    }
}
