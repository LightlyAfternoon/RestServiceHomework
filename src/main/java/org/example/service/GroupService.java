package org.example.service;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.StudentEntity;
import org.example.model.SubjectEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface GroupService {

    GroupEntity findById(int id) throws SQLException, IOException;

    boolean deleteById(int id) throws SQLException, IOException;

    GroupEntity save(GroupEntity entity) throws SQLException, IOException;

    List<GroupEntity> findAll() throws SQLException, IOException;

    List<StudentEntity> findAllStudentsWithGroupId(int id) throws SQLException, IOException;

    List<ExamEntity> findAllExamsWithGroupId(int id) throws SQLException, IOException;

    List<SubjectEntity> findAllSubjectsWithGroupId(int id) throws SQLException, IOException;
}