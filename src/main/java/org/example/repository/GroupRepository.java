package org.example.repository;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.StudentEntity;
import org.example.model.SubjectEntity;

import java.sql.SQLException;
import java.util.List;

public interface GroupRepository extends Repository<GroupEntity> {
    List<StudentEntity> findAllStudentsWithGroupId(int id) throws SQLException;

    List<ExamEntity> findAllExamsWithGroupId(int id) throws SQLException;

    List<SubjectEntity> findAllSubjectsWithGroupId(int id) throws SQLException;
}