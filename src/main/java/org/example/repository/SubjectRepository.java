package org.example.repository;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;

import java.sql.SQLException;
import java.util.List;

public interface SubjectRepository extends Repository<SubjectEntity> {
    List<TeacherEntity> findAllTeachersWithSubjectId(int id) throws SQLException;

    List<ExamEntity> findAllExamsWithSubjectId(int id) throws SQLException;

    List<GroupEntity> findAllGroupsWithSubjectId(int id) throws SQLException;

    TeacherEntity save(SubjectEntity subject, TeacherEntity teacher) throws SQLException;

    GroupEntity save(SubjectEntity subject, GroupEntity group) throws SQLException;
}