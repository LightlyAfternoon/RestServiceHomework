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
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

class ExamRepositoryTest {
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.4")
            .withInitScripts("db/AcademicPerformanceDb.sql", "db/InsertSQL.sql");
    
    private static final Logger log = LoggerFactory.getLogger(ExamRepositoryTest.class);
    Connection connection;
    AnnotationConfigApplicationContext context;

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

        context = new AnnotationConfigApplicationContext(MyTestConfig.class);
        examRepository = context.getBean(ExamRepository.class);

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

        Assertions.assertNull(examRepository.findById(50));
    }

    @Test
    void saveExamTest() throws SQLException {
        Calendar calendar = new GregorianCalendar(2023, Calendar.MAY, 26);
        Date startDate = new Date(calendar.getTimeInMillis());

        TeacherEntity teacher = new TeacherEntity(2, "t", "t", "t");
        SubjectEntity subject = new SubjectEntity(1, "s");
        GroupEntity group = new GroupEntity(2, "t", startDate, startDate, teacher);

        ExamEntity exam = examRepository.findById(2);
        exam.setStartDate(startDate);
        exam.setGroup(group);
        exam.setTeacher(teacher);
        exam.setSubject(subject);

        exam = examRepository.save(exam);
        Assertions.assertEquals(startDate, exam.getStartDate());
        Assertions.assertEquals(2, exam.getGroup().getId());
        Assertions.assertEquals(1, exam.getSubject().getId());
        Assertions.assertEquals(2, exam.getTeacher().getId());

        exam = new ExamEntity();
        calendar.add(Calendar.MONTH, 8);
        startDate = new Date(calendar.getTimeInMillis());
        exam.setStartDate(startDate);
        teacher.setId(1);
        exam.setGroup(group);
        subject.setId(3);
        exam.setSubject(subject);
        exam.setTeacher(teacher);

        exam = examRepository.save(exam);
        Assertions.assertEquals(startDate, exam.getStartDate());
        Assertions.assertEquals(2, exam.getGroup().getId());
        Assertions.assertEquals(3, exam.getSubject().getId());
        Assertions.assertEquals(1, exam.getTeacher().getId());
    }

    @Test
    void findAllExamsTest() throws SQLException {
        Set<ExamEntity> exams = examRepository.findAll();

        Assertions.assertFalse(exams.isEmpty());
    }
}
