package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.config.MyWebConfig;
import org.example.db.ConnectionManager;
import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

class TeacherRepositoryTest {
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.4")
            .withInitScripts("db/AcademicPerformanceDb.sql", "db/InsertSQL.sql");

    static final Logger log = LoggerFactory.getLogger(TeacherRepositoryTest.class);
    Connection connection;
    AnnotationConfigApplicationContext context;

    @Autowired
    TeacherRepository teacherRepository;

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
            context = new AnnotationConfigApplicationContext(MyWebConfig.class);
            teacherRepository = context.getBean(TeacherRepository.class);
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
    void getTeacherByIdTest() throws SQLException {
        TeacherEntity teacherEntity = teacherRepository.findById(1);

        EntityManagerFactory entityManagerFactory = (EntityManagerFactory) context.getBean("entityManagerFactory");
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            List<TeacherEntity> teacher = entityManager.createQuery("from TeacherEntity t left join fetch t.groups where t.id in :id").setParameter("id", 1).getResultList();
            teacher = entityManager.createQuery("from TeacherEntity t left join fetch t.subjects where t.id in :id").setParameter("id", 1).getResultList();
            teacher = entityManager.createQuery("from TeacherEntity t left join fetch t.exams where t.id in :id").setParameter("id", 1).getResultList();
            teacherEntity =  teacher.getFirst();
        }

        Assertions.assertNotNull(teacherEntity);
        Assertions.assertFalse(teacherEntity.getSubjects().isEmpty());
        Assertions.assertFalse(teacherEntity.getGroups().isEmpty());
        Assertions.assertFalse(teacherEntity.getExams().isEmpty());

        teacherEntity = teacherRepository.findById(2);
        Assertions.assertNotNull(teacherEntity);

        Assertions.assertNull(teacherRepository.findById(50));
    }

    @Test
    void deleteTeacherByIdTest() {
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> teacherRepository.deleteById(1));
        Assertions.assertDoesNotThrow(() -> teacherRepository.deleteById(3));
        Assertions.assertDoesNotThrow(() -> teacherRepository.deleteById(50));
    }

    @Test
    void saveTeacherTest() throws SQLException {
        TeacherEntity teacher = teacherRepository.findById(2);
        teacher.setFirstName("Тест");
        teacher.setPatronymic("Тестович");

        teacher = teacherRepository.save(teacher);
        Assertions.assertEquals("Тест", teacher.getFirstName());
        Assertions.assertEquals("Тестович", teacher.getPatronymic());

        teacher = new TeacherEntity();
        teacher.setFirstName("Новая");
        teacher.setLastName("Тестовна");

        teacher = teacherRepository.save(teacher);
        Assertions.assertEquals(4, teacher.getId());
        Assertions.assertEquals("Новая", teacher.getFirstName());
        Assertions.assertEquals("Тестовна", teacher.getLastName());
    }

    @Test
    void findAllTeachersTest() throws SQLException {
        List<TeacherEntity> teachers = teacherRepository.findAll();

        Assertions.assertFalse(teachers.isEmpty());
    }

    @Ignore
    @Test
    void findAllSubjectsByIdTest() throws SQLException {
        List<SubjectEntity> subjects = teacherRepository.findAllSubjectsById(1);

        Assertions.assertFalse(subjects.isEmpty());
    }

    @Ignore
    @Test
    void findAllGroupsByIdTest() throws SQLException {
        List<GroupEntity> groups = teacherRepository.findAllGroupsById(1);

        Assertions.assertFalse(groups.isEmpty());
    }

    @Ignore
    @Test
    void findAllExamsByIdTest() throws SQLException {
        List<ExamEntity> exams = teacherRepository.findAllExamsById(1);

        Assertions.assertFalse(exams.isEmpty());
    }
}