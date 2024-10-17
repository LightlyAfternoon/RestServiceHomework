package org.example.service;

import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.servlet.dto.ExamDTO;
import org.example.servlet.dto.GroupDTO;
import org.example.servlet.dto.SubjectDTO;
import org.example.servlet.dto.TeacherDTO;

import java.sql.SQLException;
import java.util.List;

public interface SubjectService {
    SubjectDTO findById(int id) throws SQLException;

    void deleteById(int id) throws SQLException;

    SubjectDTO save(SubjectEntity entity) throws SQLException;

    List<SubjectDTO> findAll() throws SQLException;

    List<TeacherDTO> findAllTeachersWithSubjectId(int id) throws SQLException;

    List<GroupDTO> findAllGroupsWithSubjectId(int id) throws SQLException;

    List<ExamDTO> findAllExamsWithSubjectId(int id) throws SQLException;

    TeacherDTO save(SubjectEntity subject, TeacherEntity teacher) throws SQLException;

    GroupDTO save(SubjectEntity subject, GroupEntity group) throws SQLException;
}
