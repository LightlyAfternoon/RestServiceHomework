package org.example.service;

import org.example.servlet.dto.ExamDTO;
import org.example.servlet.dto.GroupDTO;
import org.example.servlet.dto.StudentDTO;
import org.example.servlet.dto.SubjectDTO;

import java.sql.SQLException;
import java.util.Set;

public interface GroupService {

    GroupDTO findById(int id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    GroupDTO save(GroupDTO groupDTO) throws SQLException;

    GroupDTO save(GroupDTO groupDTO, int id) throws SQLException;

    Set<GroupDTO> findAll() throws SQLException;

    Set<StudentDTO> findAllStudentsWithGroupId(int id) throws SQLException;

    Set<ExamDTO> findAllExamsWithGroupId(int id) throws SQLException;

    Set<SubjectDTO> findAllSubjectsWithGroupId(int id) throws SQLException;
}