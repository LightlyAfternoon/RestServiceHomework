package org.example.service;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.StudentEntity;
import org.example.model.SubjectEntity;

import java.sql.SQLException;
import java.util.List;

public interface GroupService {

    GroupEntity findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    GroupEntity save(GroupEntity entity) throws SQLException;

    List<GroupEntity> findAll() throws SQLException;

    List<StudentEntity> findAllStudentsWithGroupId(int id) throws SQLException;

    List<ExamEntity> findAllExamsWithGroupId(int id) throws SQLException;

    List<SubjectEntity> findAllSubjectsWithGroupId(int id) throws SQLException;
}