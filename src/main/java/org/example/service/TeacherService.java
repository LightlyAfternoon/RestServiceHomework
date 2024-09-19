package org.example.service;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;

import java.sql.SQLException;
import java.util.List;

public interface TeacherService {
    TeacherEntity findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    TeacherEntity save(TeacherEntity entity) throws SQLException;

    List<TeacherEntity> findAll() throws SQLException;

    List<GroupEntity> findAllGroupsWithTeacherId(int id) throws SQLException;

    List<SubjectEntity> findAllSubjectsWithTeacherId(int id) throws SQLException;

    List<ExamEntity> findAllExamsWithTeacherId(int id) throws SQLException;
}
