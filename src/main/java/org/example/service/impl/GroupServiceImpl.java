package org.example.service.impl;

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
import java.util.Set;
import java.util.stream.Collectors;

public class GroupServiceImpl implements GroupService {
    GroupRepository groupRepository;

    GroupDTOMapper groupMapper = GroupDTOMapper.INSTANCE;
    StudentDTOMapper studentMapper = StudentDTOMapper.INSTANCE;
    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;
    SubjectDTOMapper subjectMapper = SubjectDTOMapper.INSTANCE;

    @Autowired
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
    public GroupDTO save(GroupDTO groupDTO) throws SQLException {
        return groupMapper.mapToDTO(groupRepository.save(groupMapper.mapToEntity(groupDTO)));
    }

    @Override
    public GroupDTO save(GroupDTO groupDTO, int id) throws SQLException {
        return groupMapper.mapToDTO(groupRepository.save(groupMapper.mapToEntity(groupDTO, id)));
    }

    @Override
    public Set<GroupDTO> findAll() throws SQLException {
        return groupRepository.findAll().stream().map(groupMapper::mapToDTO).collect(Collectors.toSet());
    }

    @Override
    public Set<StudentDTO> findAllStudentsWithGroupId(int id) throws SQLException {
        return groupRepository.findById(id).getStudents().stream().map(studentMapper::mapToDTO).collect(Collectors.toSet());
    }

    @Override
    public Set<ExamDTO> findAllExamsWithGroupId(int id) throws SQLException {
        return groupRepository.findById(id).getExams().stream().map(examMapper::mapToDTO).collect(Collectors.toSet());
    }

    @Override
    public Set<SubjectDTO> findAllSubjectsWithGroupId(int id) throws SQLException {
        return groupRepository.findById(id).getSubjects().stream().map(subjectMapper::mapToDTO).collect(Collectors.toSet());
    }
}