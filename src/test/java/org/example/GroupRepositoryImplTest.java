package org.example;

import org.example.db.ConnectionManager;
import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.StudentEntity;
import org.example.model.TeacherEntity;
import org.example.repository.GroupRepository;
import org.example.repository.impl.GroupRepositoryImpl;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

class GroupRepositoryImplTest {
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.4")
            .withInitScripts("db/AcademicPerformanceDb.sql", "db/InsertSQL.sql");

    private static final Logger log = LoggerFactory.getLogger(GroupRepositoryImplTest.class);
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
    void getGroupByIdTest() throws SQLException, IOException {
        GroupRepository groupRepository = new GroupRepositoryImpl();

        GroupEntity group = groupRepository.findById(1);
        Assertions.assertNotNull(group);
        Assertions.assertFalse(group.getStudents().isEmpty());
        Assertions.assertFalse(group.getExams().isEmpty());

        group = groupRepository.findById(2);
        Assertions.assertNotNull(group);

        Assertions.assertThrows(SQLException.class, () -> groupRepository.findById(50));
    }

    @Test
    void deleteGroupByIdTest() throws SQLException, IOException {
        GroupRepository groupRepository = new GroupRepositoryImpl();

        Assertions.assertTrue(groupRepository.deleteById(3));
        Assertions.assertFalse(groupRepository.deleteById(50));
    }

    @Test
    void saveGroupTest() throws SQLException, IOException {
        GroupRepository groupRepository = new GroupRepositoryImpl();
        Calendar startCalendar = new GregorianCalendar(2024, Calendar.SEPTEMBER,1);
        Calendar endCalendar = new GregorianCalendar(2024, Calendar.SEPTEMBER,1);


        Date startDate = new Date(startCalendar.getTimeInMillis());
        endCalendar.add(Calendar.MONTH, 46);
        Date endDate = new Date(endCalendar.getTimeInMillis());
        GroupEntity group = groupRepository.findById(2);
        group.setName("Т");
        group.setStartDate(startDate);
        group.setEndDate(endDate);
        group.setTeacherId(2);

        group = groupRepository.save(group);
        Assertions.assertEquals("Т", group.getName());
        Assertions.assertEquals(startDate, group.getStartDate());
        Assertions.assertEquals(endDate, group.getEndDate());
        Assertions.assertEquals(2, group.getTeacherId());

        startCalendar.add(Calendar.YEAR, -4);
        startDate = new Date(startCalendar.getTimeInMillis());
        endCalendar.add(Calendar.MONTH, -48);
        endDate = new Date(endCalendar.getTimeInMillis());
        group = new GroupEntity();
        group.setName("Т-2");
        group.setStartDate(startDate);
        group.setEndDate(endDate);
        group.setTeacherId(3);

        group = groupRepository.save(group);
        Assertions.assertEquals(4, group.getId());
        Assertions.assertEquals("Т-2", group.getName());
        Assertions.assertEquals(startDate, group.getStartDate());
        Assertions.assertEquals(endDate, group.getEndDate());
        Assertions.assertEquals(3, group.getTeacherId());
    }

    @Test
    void findAllGroupsTest() throws SQLException, IOException {
        GroupRepository groupRepository = new GroupRepositoryImpl();
        List<GroupEntity> groups = groupRepository.findAll();

        Assertions.assertFalse(groups.isEmpty());
    }

    @Test
    void findAllStudentsWithGroupIdTest() throws SQLException, IOException {
        GroupRepository groupRepository = new GroupRepositoryImpl();
        List<StudentEntity> students = groupRepository.findAllStudentsWithGroupId(1);

        Assertions.assertFalse(students.isEmpty());
    }

    @Test
    void findAllExamsWithGroupIdTest() throws SQLException, IOException {
        GroupRepository groupRepository = new GroupRepositoryImpl();
        List<ExamEntity> exams = groupRepository.findAllExamsWithGroupId(2);

        Assertions.assertEquals(2, exams.size());
    }
}