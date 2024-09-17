package org.example.repository;

import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SubjectRepository extends Repository<SubjectEntity> {
    List<TeacherEntity> findAllTeachersWithSubjectId(int id) throws SQLException, IOException;

    Set<Map.Entry<SubjectEntity, TeacherEntity>> save(SubjectEntity subject, TeacherEntity teacher) throws SQLException, IOException;
}