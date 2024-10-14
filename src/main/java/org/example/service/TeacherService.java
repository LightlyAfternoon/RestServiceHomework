package org.example.service;

import org.example.model.TeacherEntity;
import org.example.servlet.dto.ExamDTO;
import org.example.servlet.dto.GroupDTO;
import org.example.servlet.dto.SubjectDTO;
import org.example.servlet.dto.TeacherDTO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface TeacherService {
    TeacherDTO findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    TeacherDTO save(TeacherEntity entity) throws SQLException;

    List<TeacherDTO> findAll() throws SQLException;

    List<GroupDTO> findAllGroupsWithTeacherId(int id) throws SQLException;

    List<SubjectDTO> findAllSubjectsWithTeacherId(int id) throws SQLException;

    List<ExamDTO> findAllExamsWithTeacherId(int id) throws SQLException;
}
