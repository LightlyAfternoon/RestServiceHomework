package org.example.service;

import org.example.controller.dto.ExamDTO;
import org.example.controller.dto.GroupDTO;
import org.example.controller.dto.SubjectDTO;
import org.example.controller.dto.TeacherDTO;

import java.sql.SQLException;
import java.util.Set;

public interface TeacherService {
    TeacherDTO findById(int id) throws SQLException;

    void deleteById(int id) throws SQLException;

    TeacherDTO save(TeacherDTO teacherDTO) throws SQLException;

    TeacherDTO save(TeacherDTO teacherDTO, int id) throws SQLException;

    Set<TeacherDTO> findAll() throws SQLException;

    Set<GroupDTO> findAllGroupsWithTeacherId(int id) throws SQLException;

    Set<SubjectDTO> findAllSubjectsWithTeacherId(int id) throws SQLException;

    Set<ExamDTO> findAllExamsWithTeacherId(int id) throws SQLException;
}
