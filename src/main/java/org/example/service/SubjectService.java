package org.example.service;

import org.example.servlet.dto.ExamDTO;
import org.example.servlet.dto.GroupDTO;
import org.example.servlet.dto.SubjectDTO;
import org.example.servlet.dto.TeacherDTO;

import java.sql.SQLException;
import java.util.Set;

public interface SubjectService {
    SubjectDTO findById(int id) throws SQLException;

    void deleteById(int id) throws SQLException;

    SubjectDTO save(SubjectDTO subjectDTO) throws SQLException;

    SubjectDTO save(SubjectDTO subjectDTO, int id) throws SQLException;

    Set<SubjectDTO> findAll() throws SQLException;

    Set<TeacherDTO> findAllTeachersWithSubjectId(int id) throws SQLException;

    Set<GroupDTO> findAllGroupsWithSubjectId(int id) throws SQLException;

    Set<ExamDTO> findAllExamsWithSubjectId(int id) throws SQLException;
}
