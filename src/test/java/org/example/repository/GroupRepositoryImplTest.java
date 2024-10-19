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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

class GroupRepositoryImplTest {
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.4")
            .withInitScripts("db/AcademicPerformanceDb.sql", "db/InsertSQL.sql");

    static final Logger log = LoggerFactory.getLogger(GroupRepositoryImplTest.class);
    Connection connection;

    @Autowired
    GroupRepository groupRepository;

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
    void getGroupByIdTest() throws SQLException {
        GroupEntity group = groupRepository.findById(1);
        Assertions.assertNotNull(group);
        Assertions.assertFalse(group.getStudents().isEmpty());
        Assertions.assertFalse(group.getExams().isEmpty());
        Assertions.assertFalse(group.getSubjects().isEmpty());

        group = groupRepository.findById(2);
        Assertions.assertNotNull(group);

        Assertions.assertNull(groupRepository.findById(50));
    }

    @Test
    void saveGroupTest() throws SQLException {
        Calendar startCalendar = new GregorianCalendar(2024, Calendar.SEPTEMBER,1);
        Calendar endCalendar = new GregorianCalendar(2024, Calendar.SEPTEMBER,1);

        TeacherEntity teacher = new TeacherEntity(2, "t", "t", "t");

        Date startDate = new Date(startCalendar.getTimeInMillis());
        endCalendar.add(Calendar.MONTH, 46);
        Date endDate = new Date(endCalendar.getTimeInMillis());
        GroupEntity group = groupRepository.findById(2);
        group.setName("Т");
        group.setStartDate(startDate);
        group.setEndDate(endDate);
        group.setTeacher(teacher);

        group = groupRepository.save(group);
        Assertions.assertEquals("Т", group.getName());
        Assertions.assertEquals(startDate, group.getStartDate());
        Assertions.assertEquals(endDate, group.getEndDate());
        Assertions.assertEquals(2, group.getTeacher().getId());

        startCalendar.add(Calendar.YEAR, -4);
        startDate = new Date(startCalendar.getTimeInMillis());
        endCalendar.add(Calendar.MONTH, -48);
        endDate = new Date(endCalendar.getTimeInMillis());
        group = new GroupEntity();
        group.setName("Т-2");
        group.setStartDate(startDate);
        group.setEndDate(endDate);
        teacher.setId(3);
        group.setTeacher(teacher);

        group = groupRepository.save(group);
        Assertions.assertEquals(4, group.getId());
        Assertions.assertEquals("Т-2", group.getName());
        Assertions.assertEquals(startDate, group.getStartDate());
        Assertions.assertEquals(endDate, group.getEndDate());
        Assertions.assertEquals(3, group.getTeacher().getId());
    }

    @Test
    void findAllGroupsTest() throws SQLException {
        Set<GroupEntity> groups = groupRepository.findAll();

        Assertions.assertFalse(groups.isEmpty());
    }

    @Test
    void findAllStudentsWithGroupIdTest() throws SQLException {
        Set<StudentEntity> students = groupRepository.findById(1).getStudents();

        Assertions.assertFalse(students.isEmpty());
    }

    @Test
    void findAllExamsWithGroupIdTest() throws SQLException {
        Set<ExamEntity> exams = groupRepository.findById(2).getExams();

        Assertions.assertEquals(2, exams.size());
    }

    @Test
    void findAllSubjectsWithGroupIdTest() throws SQLException {
        Set<SubjectEntity> subjects = groupRepository.findById(2).getSubjects();

        Assertions.assertEquals(1, subjects.size());
    }
}