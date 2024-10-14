package org.example.service.impl;

import org.example.model.GroupEntity;
import org.example.repository.GroupRepository;
import org.example.service.GroupService;
import org.example.servlet.dto.ExamDTO;
import org.example.servlet.dto.GroupDTO;
import org.example.servlet.dto.StudentDTO;
import org.example.servlet.dto.SubjectDTO;
import org.example.servlet.mapper.ExamDTOMapper;
import org.example.servlet.mapper.GroupDTOMapper;
import org.example.servlet.mapper.StudentDTOMapper;
import org.example.servlet.mapper.SubjectDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public class GroupServiceImpl implements GroupService {
    @Autowired
    GroupRepository groupRepository;

    GroupDTOMapper groupMapper = GroupDTOMapper.INSTANCE;
    StudentDTOMapper studentMapper = StudentDTOMapper.INSTANCE;
    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;
    SubjectDTOMapper subjectMapper = SubjectDTOMapper.INSTANCE;

    public GroupServiceImpl() {
    }

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public GroupDTO findById(int id) throws SQLException {
        return groupMapper.mapToDTO(groupRepository.findById(id));
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return groupRepository.deleteById(id);
    }

    @Override
    public GroupDTO save(GroupEntity entity) throws SQLException {
        return groupMapper.mapToDTO(groupRepository.save(entity));
    }

    @Override
    public List<GroupDTO> findAll() throws SQLException {
        return groupRepository.findAll().stream().map(groupMapper::mapToDTO).toList();
    }

    @Override
    public List<StudentDTO> findAllStudentsWithGroupId(int id) throws SQLException {
        return groupRepository.findAllStudentsWithGroupId(id).stream().map(studentMapper::mapToDTO).toList();
    }

    @Override
    public List<ExamDTO> findAllExamsWithGroupId(int id) throws SQLException {
        return groupRepository.findAllExamsWithGroupId(id).stream().map(examMapper::mapToDTO).toList();
    }

    @Override
    public List<SubjectDTO> findAllSubjectsWithGroupId(int id) throws SQLException {
        return groupRepository.findAllSubjectsWithGroupId(id).stream().map(subjectMapper::mapToDTO).toList();
    }
}