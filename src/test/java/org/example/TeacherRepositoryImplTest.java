package org.example;

import org.example.db.ConnectionManager;
import org.example.model.TeacherEntity;
import org.example.repository.impl.TeacherRepositoryImpl;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

class TeacherRepositoryImplTest {
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.4")
            .withInitScripts("db/AcademicPerformanceDb.sql", "db/InsertSQL.sql");

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
        System.out.println("Connection is OK!");
    }

    @Test
    void getTeacherByIdTest() {
        TeacherRepositoryImpl teacherRepository = new TeacherRepositoryImpl();

        TeacherEntity teacher = teacherRepository.findById(1);
        Assertions.assertNotNull(teacher);

        teacher = teacherRepository.findById(2);
        Assertions.assertNotNull(teacher);

        Assertions.assertThrows(RuntimeException.class, () -> teacherRepository.findById(3));
    }

    @Test
    void deleteTeacherByIdTest() {
        TeacherRepositoryImpl teacherRepository = new TeacherRepositoryImpl();

        Assertions.assertTrue(teacherRepository.deleteById(2));
        Assertions.assertFalse(teacherRepository.deleteById(3));
    }
}