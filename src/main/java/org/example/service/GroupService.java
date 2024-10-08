package org.example.service;

import org.example.model.GroupEntity;
import org.example.servlet.dto.ExamDTO;
import org.example.servlet.dto.GroupDTO;
import org.example.servlet.dto.StudentDTO;
import org.example.servlet.dto.SubjectDTO;

import java.sql.SQLException;
import java.util.List;

public interface GroupService {

    GroupDTO findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    GroupDTO save(GroupEntity entity) throws SQLException;

    List<GroupDTO> findAll() throws SQLException;

    List<StudentDTO> findAllStudentsWithGroupId(int id) throws SQLException;

    List<ExamDTO> findAllExamsWithGroupId(int id) throws SQLException;

    List<SubjectDTO> findAllSubjectsWithGroupId(int id) throws SQLException;
}