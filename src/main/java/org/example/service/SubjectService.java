package org.example.service;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface SubjectService {
    SubjectEntity findById(int id) throws SQLException, IOException;

    boolean deleteById(int id) throws SQLException, IOException;

    SubjectEntity save(SubjectEntity entity) throws SQLException, IOException;

    List<SubjectEntity> findAll() throws SQLException, IOException;

    List<TeacherEntity> findAllTeachersWithSubjectId(int id) throws SQLException, IOException;

    List<GroupEntity> findAllGroupsWithSubjectId(int id) throws SQLException, IOException;

    List<ExamEntity> findAllExamsWithSubjectId(int id) throws SQLException, IOException;

    TeacherEntity save(SubjectEntity subject, TeacherEntity teacher) throws SQLException, IOException;

    GroupEntity save(SubjectEntity subject, GroupEntity group) throws SQLException, IOException;
}
