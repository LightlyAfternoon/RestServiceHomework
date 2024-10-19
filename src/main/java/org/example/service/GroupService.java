package org.example.service;

import org.example.controller.dto.ExamDTO;
import org.example.controller.dto.GroupDTO;
import org.example.controller.dto.StudentDTO;
import org.example.controller.dto.SubjectDTO;

import java.sql.SQLException;
import java.util.Set;

public interface GroupService {

    GroupDTO findById(int id) throws SQLException;

    void deleteById(int id) throws SQLException;

    GroupDTO save(GroupDTO groupDTO) throws SQLException;

    GroupDTO save(GroupDTO groupDTO, int id) throws SQLException;

    Set<GroupDTO> findAll() throws SQLException;

    Set<StudentDTO> findAllStudentsWithGroupId(int id) throws SQLException;

    Set<ExamDTO> findAllExamsWithGroupId(int id) throws SQLException;

    Set<SubjectDTO> findAllSubjectsWithGroupId(int id) throws SQLException;
}