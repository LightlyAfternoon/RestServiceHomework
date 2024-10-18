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
import java.sql.SQLException;
import java.util.Set;

class SubjectRepositoryTest {
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.4")
            .withInitScripts("db/AcademicPerformanceDb.sql", "db/InsertSQL.sql");

    static final Logger log = LoggerFactory.getLogger(SubjectRepositoryTest.class);
    Connection connection;
    AnnotationConfigApplicationContext context;

    SubjectRepository subjectRepository;
    GroupRepository groupRepository;
    TeacherRepository teacherRepository;

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
            context = new AnnotationConfigApplicationContext(MyTestConfig.class);
            teacherRepository = context.getBean(TeacherRepository.class);
            groupRepository = context.getBean(GroupRepository.class);
            subjectRepository = context.getBean(SubjectRepository.class);
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
    void getSubjectByIdTest() throws SQLException {
        SubjectEntity subject = subjectRepository.findById(1);
        log.info("---");

        Assertions.assertNotNull(subject);
        Assertions.assertFalse(subject.getTeachers().isEmpty());
        Assertions.assertFalse(subject.getExams().isEmpty());
        Assertions.assertFalse(subject.getGroups().isEmpty());

        subject = subjectRepository.findById(2);
        Assertions.assertNotNull(subject);

        log.info("---");
        Assertions.assertNull(subjectRepository.findById(50));
    }

    @Test
    void deleteSubjectByIdTest() throws SQLException {
        log.info("---");
        Assertions.assertNotNull(subjectRepository.findById(1));
        subjectRepository.deleteById(1);
        Assertions.assertNotNull(subjectRepository.findById(1));
        log.info("---");
        Assertions.assertNotNull(subjectRepository.findById(3));
        subjectRepository.deleteById(3);
        Assertions.assertNull(subjectRepository.findById(3));
        log.info("---");
        Assertions.assertNull(subjectRepository.findById(50));
        subjectRepository.deleteById(50);
        Assertions.assertNull(subjectRepository.findById(50));
    }

    @Test
    void saveSubjectTest() throws SQLException {
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
    void saveSubjectTeacherRelationshipTest() throws SQLException {
        SubjectEntity subject = subjectRepository.findById(1);
        TeacherEntity teacher = teacherRepository.findById(3);

        Set<TeacherEntity> teacherEntities = subject.getTeachers();
        if (!teacherEntities.contains(teacher)) {
            teacherEntities.add(teacher);
            subject.setTeachers(teacherEntities);
            subjectRepository.save(subject);
        }

        Assertions.assertEquals(teacherEntities, subjectRepository.findById(1).getTeachers());
    }

    @Test
    void saveSubjectGroupRelationshipTest() throws SQLException {
        SubjectEntity subject = subjectRepository.findById(2);
        GroupEntity group = groupRepository.findById(1);

        Set<GroupEntity> groupEntities = subject.getGroups();
        System.out.println("---");
        if (!groupEntities.contains(group)) {
            groupEntities.add(group);
            subject.setGroups(groupEntities);
            subjectRepository.save(subject);
        }

        Assertions.assertEquals(groupEntities, subjectRepository.findById(2).getGroups());
    }

    @Test
    void findAllSubjectsTest() throws SQLException {
        Set<SubjectEntity> subjects = subjectRepository.findAll();

        Assertions.assertFalse(subjects.isEmpty());
    }

    @Test
    void findAllTeachersWithSubjectIdTest() throws SQLException {
        Set<TeacherEntity> teachers = subjectRepository.findById(1).getTeachers();

        Assertions.assertFalse(teachers.isEmpty());
    }

    @Test
    void findAllExamsWithSubjectIdTest() throws SQLException {
        Set<ExamEntity> exams = subjectRepository.findById(1).getExams();

        Assertions.assertFalse(exams.isEmpty());
    }

    @Test
    void findAllGroupsWithSubjectIdTest() throws SQLException {
        Set<GroupEntity> exams = subjectRepository.findById(1).getGroups();

        Assertions.assertFalse(exams.isEmpty());
    }
}