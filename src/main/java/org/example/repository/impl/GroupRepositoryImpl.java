package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.StudentEntity;
import org.example.model.SubjectEntity;
import org.example.repository.GroupRepository;
import org.example.repository.mapper.ExamResultSetMapperImpl;
import org.example.repository.mapper.GroupResultSetMapperImpl;
import org.example.repository.mapper.StudentResultSetMapperImpl;
import org.example.repository.mapper.SubjectResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupRepositoryImpl implements GroupRepository {
    @Override
    public GroupEntity findById(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"group\" WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            GroupEntity groupEntity = new GroupResultSetMapperImpl().map(resultSet);
            groupEntity.setStudents(findAllStudentsWithGroupId(id));
            groupEntity.setExams(findAllExamsWithGroupId(id));
            groupEntity.setSubjects(findAllSubjectsWithGroupId(id));

            return groupEntity;
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM \"group\" WHERE id = ?")) {

            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public GroupEntity save(GroupEntity entity) throws SQLException {
        if (entity.getId() > 0) {
            return update(entity);
        } else {
            return insert(entity);
        }
    }

    private GroupEntity insert(GroupEntity entity) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO \"group\" (name, start_date, end_date, teacher_id) VALUES (?, ?, ?, ?)")) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDate(2, entity.getStartDate());
            preparedStatement.setDate(3, entity.getEndDate());
            preparedStatement.setInt(4, entity.getTeacherId());
            preparedStatement.executeUpdate();

            try (PreparedStatement newPreparedStatement = connection.prepareStatement("SELECT * FROM \"group\" ORDER BY id DESC LIMIT 1")) {
                ResultSet resultSet = newPreparedStatement.executeQuery();

                if (!resultSet.next()) {
                    return null;
                }

                GroupEntity group = new GroupResultSetMapperImpl().map(resultSet);
                group.setStudents(findAllStudentsWithGroupId(group.getId()));
                group.setExams(findAllExamsWithGroupId(group.getId()));

                return group;
            }
        }
    }

    private GroupEntity update(GroupEntity entity) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE \"group\" SET name = ?, start_date = ?, end_date = ?, teacher_id = ? WHERE id = ?")) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDate(2, entity.getStartDate());
            preparedStatement.setDate(3, entity.getEndDate());
            preparedStatement.setInt(4, entity.getTeacherId());
            preparedStatement.setInt(5, entity.getId());
            preparedStatement.executeUpdate();

            return entity;
        }
    }

    @Override
    public List<GroupEntity> findAll() throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"group\"")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<GroupEntity> groups = new ArrayList<>();

            while (resultSet.next()) {
                GroupEntity groupEntity = new GroupResultSetMapperImpl().map(resultSet);
                groupEntity.setStudents(findAllStudentsWithGroupId(groupEntity.getId()));
                groupEntity.setExams(findAllExamsWithGroupId(groupEntity.getId()));

                groups.add(groupEntity);
            }

            return groups;
        }
    }

    @Override
    public List<StudentEntity> findAllStudentsWithGroupId(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT s.* FROM student s JOIN \"group\" g ON s.group_id = g.id WHERE g.id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<StudentEntity> students = new ArrayList<>();

            while (resultSet.next()) {
                students.add(new StudentResultSetMapperImpl().map(resultSet));
            }

            return students;
        }
    }

    @Override
    public List<ExamEntity> findAllExamsWithGroupId(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT e.* FROM exam e JOIN \"group\" g ON e.group_id = g.id WHERE g.id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ExamEntity> exams = new ArrayList<>();

            while (resultSet.next()) {
                exams.add(new ExamResultSetMapperImpl().map(resultSet));
            }

            return exams;
        }
    }

    @Override
    public List<SubjectEntity> findAllSubjectsWithGroupId(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT s.* FROM subject_group sg " +
                     "JOIN subject s ON s.id = sg.subject_id WHERE sg.group_id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<SubjectEntity> subjectEntities = new ArrayList<>();

            while (resultSet.next()) {
                subjectEntities.add(new SubjectResultSetMapperImpl().map(resultSet));
            }

            return subjectEntities;
        }
    }
}