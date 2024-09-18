package org.example.repository;

import org.example.model.ExamEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface SubjectRepository extends Repository<SubjectEntity> {
    List<TeacherEntity> findAllTeachersWithSubjectId(int id) throws SQLException, IOException;
    List<ExamEntity> findAllExamsWithSubjectId(int id) throws SQLException, IOException;

    TeacherEntity save(SubjectEntity subject, TeacherEntity teacher) throws SQLException, IOException;
}