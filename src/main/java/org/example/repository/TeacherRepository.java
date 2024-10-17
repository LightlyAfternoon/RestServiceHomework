package org.example.repository;

import org.example.model.TeacherEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@org.springframework.stereotype.Repository
public interface TeacherRepository extends Repository<TeacherEntity, Integer> {
    @Transactional
    TeacherEntity findById(int id) throws SQLException;

    @Modifying
    @Transactional
    @Query(value = "delete from subject_teacher where teacher_id = :id;delete from teacher where id = :id", nativeQuery = true)
    void deleteById(@Param("id") int id);

    @Modifying
    @Transactional
    TeacherEntity save(TeacherEntity entity) throws SQLException;

    @Transactional
    List<TeacherEntity> findAll() throws SQLException;
}