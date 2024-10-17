package org.example.repository;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

public interface SubjectRepository {
    SubjectEntity findById(int id) throws SQLException;

    @Modifying
    @Transactional
    @Query(value = "delete from subject_teacher where subject_id = :id;delete from teacher where id = :id", nativeQuery = true)
    void deleteById(@Param("id") int id);

    SubjectEntity save(SubjectEntity entity) throws SQLException;

    List<SubjectEntity> findAll() throws SQLException;

    List<TeacherEntity> findAllTeachersWithSubjectId(int id) throws SQLException;

    List<ExamEntity> findAllExamsWithSubjectId(int id) throws SQLException;

    List<GroupEntity> findAllGroupsWithSubjectId(int id) throws SQLException;

    TeacherEntity save(SubjectEntity subject, TeacherEntity teacher) throws SQLException;

    GroupEntity save(SubjectEntity subject, GroupEntity group) throws SQLException;
}