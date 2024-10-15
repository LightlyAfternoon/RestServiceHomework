package org.example.repository;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.sql.SQLException;
import java.util.List;

@org.springframework.stereotype.Repository
public interface TeacherRepository extends Repository<TeacherEntity, Integer> {
    @Query("from TeacherEntity t join fetch t.subjects where t.id in :id")
    TeacherEntity findById(@Param("id") int id) throws SQLException;

    void deleteById(int id);

    TeacherEntity save(TeacherEntity entity) throws SQLException;

    List<TeacherEntity> findAll() throws SQLException;

    // need to move to other repos?
    @Query("from TeacherEntity t join fetch t.groups where t.id in :id")
    List<GroupEntity> findAllGroupsById(@Param("id")int id) throws SQLException;
    @Query("from TeacherEntity t join fetch t.subjects where t.id in :id")
    List<SubjectEntity> findAllSubjectsById(@Param("id")int id) throws SQLException;
    @Query("from TeacherEntity t join fetch t.exams where t.id in :id")
    List<ExamEntity> findAllExamsById(@Param("id")int id) throws SQLException;
}