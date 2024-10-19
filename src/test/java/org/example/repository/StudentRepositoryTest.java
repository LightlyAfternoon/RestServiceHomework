package org.example.repository;

import org.example.config.MyTestConfig;
import org.example.db.ConnectionManager;
import org.example.model.*;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

class StudentRepositoryTest {
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.4")
            .withInitScripts("db/AcademicPerformanceDb.sql", "db/InsertSQL.sql");
    
    static final Logger log = LoggerFactory.getLogger(StudentRepositoryTest.class);
    Connection connection;
    AnnotationConfigApplicationContext context;

    StudentRepository studentRepository;
    GroupRepository groupRepository;

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
            studentRepository = context.getBean(StudentRepository.class);
            groupRepository = context.getBean(GroupRepository.class);
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
    void getStudentByIdTest() throws SQLException {
        StudentEntity studentEntity = studentRepository.findById(1);
        log.info("---");

        Assertions.assertNotNull(studentEntity);

        studentEntity = studentRepository.findById(2);
        Assertions.assertNotNull(studentEntity);

        log.info("---");
        Assertions.assertNull(studentRepository.findById(50));
    }

    @Test
    void deleteStudentByIdTest() throws SQLException {
        Assertions.assertNotNull(studentRepository.findById(1));
        studentRepository.deleteById(1);
        Assertions.assertNotNull(studentRepository.findById(1));
        log.info("---");
        Assertions.assertNotNull(studentRepository.findById(3));
        studentRepository.deleteById(3);
        Assertions.assertNull(studentRepository.findById(3));
        log.info("---");
        Assertions.assertNull(studentRepository.findById(50));
        studentRepository.deleteById(50);
        Assertions.assertNull(studentRepository.findById(50));
    }

    @Test
    void saveStudentTest() throws SQLException {
        StudentEntity student = studentRepository.findById(2);
        student.setFirstName("John");
        student.setLastName("Doe");

        student = studentRepository.save(student);
        Assertions.assertEquals("John", student.getFirstName());
        Assertions.assertEquals("Doe", student.getLastName());

        GroupEntity group = groupRepository.findById(2);

        student = new StudentEntity();
        student.setFirstName("Рита");
        student.setLastName("Черная");
        student.setPatronymic("Константиновна");
        student.setGroup(group);

        student = studentRepository.save(student);
        Assertions.assertEquals(4, student.getId());
        Assertions.assertEquals("Рита", student.getFirstName());
        Assertions.assertEquals("Черная", student.getLastName());
        Assertions.assertEquals("Константиновна", student.getPatronymic());
        Assertions.assertEquals(2, student.getGroup().getId());
    }

    @Test
    void findAllStudentsTest() throws SQLException {
        Set<StudentEntity> students = studentRepository.findAll();

        Assertions.assertFalse(students.isEmpty());
    }
}