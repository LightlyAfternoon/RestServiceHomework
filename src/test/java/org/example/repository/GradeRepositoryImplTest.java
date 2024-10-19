package org.example.repository;

import org.example.db.ConnectionManager;
import org.example.model.*;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

class GradeRepositoryImplTest {
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.4")
            .withInitScripts("db/AcademicPerformanceDb.sql", "db/InsertSQL.sql");

    private static final Logger log = LoggerFactory.getLogger(GradeRepositoryImplTest.class);
    Connection connection;

    @Autowired
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
    void saveGradeTest() throws SQLException {
        GradeEntity grade = gradeRepository.findById(1);
        TeacherEntity teacher = new TeacherEntity(1, "t", "t", "t");
        GroupEntity group = new GroupEntity(1, "t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacher);
        SubjectEntity subject = new SubjectEntity(1, "t");
        StudentEntity student = new StudentEntity(2, "t", "t", "t", group);
        ExamEntity exam = new ExamEntity(1, new Date(System.currentTimeMillis()), group, subject, teacher);
        grade.setStudent(student);
        grade.setExam(exam);
        grade.setMark((short) 3);

        grade = gradeRepository.save(grade);
        Assertions.assertEquals(2, grade.getStudent().getId());
        Assertions.assertEquals(1, grade.getExam().getId());
        Assertions.assertEquals(3, grade.getMark());

        grade = new GradeEntity();
        student.setId(3);
        exam.setId(3);
        grade.setStudent(student);
        grade.setExam(exam);
        grade.setMark((short) 2);

        grade = gradeRepository.save(grade);
        Assertions.assertEquals(3, grade.getStudent().getId());
        Assertions.assertEquals(3, grade.getExam().getId());
        Assertions.assertEquals(2, grade.getMark());
    }

    @Test
    void findAllGradesTest() throws SQLException {
        Set<GradeEntity> grades = gradeRepository.findAll();

        Assertions.assertFalse(grades.isEmpty());
    }
}