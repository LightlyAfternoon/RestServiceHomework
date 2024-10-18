package org.example.service.impl;

import org.example.repository.SubjectRepository;
import org.example.service.SubjectService;
import org.example.servlet.dto.ExamDTO;
import org.example.servlet.dto.GroupDTO;
import org.example.servlet.dto.SubjectDTO;
import org.example.servlet.dto.TeacherDTO;
import org.example.servlet.mapper.ExamDTOMapper;
import org.example.servlet.mapper.GroupDTOMapper;
import org.example.servlet.mapper.SubjectDTOMapper;
import org.example.servlet.mapper.TeacherDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

public class SubjectServiceImpl implements SubjectService {
    SubjectRepository subjectRepository;

    SubjectDTOMapper subjectMapper = SubjectDTOMapper.INSTANCE;
    TeacherDTOMapper teacherMapper = TeacherDTOMapper.INSTANCE;
    GroupDTOMapper groupMapper = GroupDTOMapper.INSTANCE;
    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public SubjectDTO findById(int id) throws SQLException {
        return subjectMapper.mapToDTO(subjectRepository.findById(id));
    }

    @Override
    public void deleteById(int id) throws SQLException {
        subjectRepository.deleteById(id);
    }

    @Override
    public SubjectDTO save(SubjectDTO subjectDTO) throws SQLException {
        return subjectMapper.mapToDTO(subjectRepository.save(subjectMapper.mapToEntity(subjectDTO)));
    }

    @Override
    public SubjectDTO save(SubjectDTO subjectDTO, int id) throws SQLException {
        return subjectMapper.mapToDTO(subjectRepository.save(subjectMapper.mapToEntity(subjectDTO, id)));
    }

    @Override
    public Set<SubjectDTO> findAll() throws SQLException {
        return subjectRepository.findAll().stream().map(subjectMapper::mapToDTO).collect(Collectors.toSet());
    }

    @Override
    public Set<TeacherDTO> findAllTeachersWithSubjectId(int id) throws SQLException {
        return subjectRepository.findById(id).getTeachers().stream().map(teacherMapper::mapToDTO).collect(Collectors.toSet());
    }

    @Override
    public Set<GroupDTO> findAllGroupsWithSubjectId(int id) throws SQLException {
        return subjectRepository.findById(id).getGroups().stream().map(groupMapper::mapToDTO).collect(Collectors.toSet());
    }

    @Override
    public Set<ExamDTO> findAllExamsWithSubjectId(int id) throws SQLException {
        return subjectRepository.findById(id).getExams().stream().map(examMapper::mapToDTO).collect(Collectors.toSet());
    }
}