package org.example.repository;

import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface TeacherRepository extends Repository<TeacherEntity> {
    List<SubjectEntity> findAllSubjectsWithTeacherId(int id) throws SQLException, IOException;
}