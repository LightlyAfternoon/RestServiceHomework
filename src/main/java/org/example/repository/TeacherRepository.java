package org.example.repository;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.springframework.data.repository.Repository;

import java.sql.SQLException;
import java.util.List;

@org.springframework.stereotype.Repository
public interface TeacherRepository extends Repository<TeacherEntity, Integer> {
    TeacherEntity findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    TeacherEntity save(TeacherEntity entity) throws SQLException;

    List<TeacherEntity> findAll() throws SQLException;
    List<GroupEntity> findAllGroupsById(int id) throws SQLException;

    List<SubjectEntity> findAllSubjectsById(int id) throws SQLException;

    List<ExamEntity> findAllExamsById(int id) throws SQLException;
}