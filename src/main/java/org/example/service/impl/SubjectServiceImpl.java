package org.example.service.impl;

import org.example.repository.SubjectRepository;
import org.example.service.SubjectService;
import org.example.controller.dto.ExamDTO;
import org.example.controller.dto.GroupDTO;
import org.example.controller.dto.SubjectDTO;
import org.example.controller.dto.TeacherDTO;
import org.example.controller.mapper.ExamDTOMapper;
import org.example.controller.mapper.GroupDTOMapper;
import org.example.controller.mapper.SubjectDTOMapper;
import org.example.controller.mapper.TeacherDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
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