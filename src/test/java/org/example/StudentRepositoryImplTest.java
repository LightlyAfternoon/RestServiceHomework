package org.example;

import org.example.db.ConnectionManager;
import org.example.model.StudentEntity;
import org.example.repository.StudentRepository;
import org.example.repository.impl.StudentRepositoryImpl;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

class StudentRepositoryImplTest {
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.4")
            .withInitScripts("db/AcademicPerformanceDb.sql", "db/InsertSQL.sql");
    
    private static final Logger log = LoggerFactory.getLogger(StudentRepositoryImplTest.class);
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
        ConnectionManager connectionManager = new ConnectionManager(container.getJdbcUrl(), container.getUsername(), container.getPassword());
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
    void getStudentByIdTest() throws SQLException, IOException {
        StudentRepository studentRepository = new StudentRepositoryImpl();

        StudentEntity student = studentRepository.findById(1);
        Assertions.assertNotNull(student);

        student = studentRepository.findById(2);
        Assertions.assertNotNull(student);

        Assertions.assertThrows(SQLException.class, () -> studentRepository.findById(50));
    }

    @Test
    void deleteStudentByIdTest() throws SQLException, IOException {
        StudentRepository studentRepository = new StudentRepositoryImpl();

        Assertions.assertTrue(studentRepository.deleteById(3));
        Assertions.assertFalse(studentRepository.deleteById(50));
    }

    @Test
    void saveStudentTest() throws SQLException, IOException {
        StudentRepository studentRepository = new StudentRepositoryImpl();

        StudentEntity student = studentRepository.findById(2);
        student.setFirstName("John");
        student.setLastName("Doe");

        student = studentRepository.save(student);
        Assertions.assertEquals("John", student.getFirstName());
        Assertions.assertEquals("Doe", student.getLastName());

        student = new StudentEntity();
        student.setFirstName("Рита");
        student.setLastName("Черная");
        student.setPatronymic("Константиновна");
        student.setGroupId(2);

        student = studentRepository.save(student);
        Assertions.assertEquals(4, student.getId());
        Assertions.assertEquals("Рита", student.getFirstName());
        Assertions.assertEquals("Черная", student.getLastName());
        Assertions.assertEquals("Константиновна", student.getPatronymic());
        Assertions.assertEquals(2, student.getGroupId());
    }

    @Test
    void findAllStudentsTest() throws SQLException, IOException {
        StudentRepository studentRepository = new StudentRepositoryImpl();
        List<StudentEntity> students = studentRepository.findAll();

        Assertions.assertFalse(students.isEmpty());
    }
}
