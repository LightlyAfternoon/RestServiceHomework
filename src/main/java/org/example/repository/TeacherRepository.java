package org.example.repository;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;

import java.sql.SQLException;
import java.util.List;

public interface TeacherRepository extends Repository<TeacherEntity> {
    List<GroupEntity> findAllGroupsWithTeacherId(int id) throws SQLException;

    List<SubjectEntity> findAllSubjectsWithTeacherId(int id) throws SQLException;

    List<ExamEntity> findAllExamsWithTeacherId(int id) throws SQLException;
}