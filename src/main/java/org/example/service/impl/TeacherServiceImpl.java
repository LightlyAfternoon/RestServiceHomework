package org.example.service.impl;

import org.example.repository.TeacherRepository;
import org.example.service.TeacherService;
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
public class TeacherServiceImpl implements TeacherService {
    TeacherRepository teacherRepository;

    TeacherDTOMapper teacherMapper = TeacherDTOMapper.INSTANCE;
    GroupDTOMapper groupMapper = GroupDTOMapper.INSTANCE;
    SubjectDTOMapper subjectMapper = SubjectDTOMapper.INSTANCE;
    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public TeacherDTO findById(int id) throws SQLException {
        return teacherMapper.mapToDTO(teacherRepository.findById(id));
    }

    @Override
    public void deleteById(int id) throws SQLException {
        teacherRepository.deleteById(id);
    }

    @Override
    public TeacherDTO save(TeacherDTO teacherDTO) throws SQLException {
        return teacherMapper.mapToDTO(teacherRepository.save(teacherMapper.mapToEntity(teacherDTO)));
    }

    @Override
    public TeacherDTO save(TeacherDTO teacherDTO, int id) throws SQLException {
        return teacherMapper.mapToDTO(teacherRepository.save(teacherMapper.mapToEntity(teacherDTO, id)));
    }

    @Override
    public Set<TeacherDTO> findAll() throws SQLException {
        return teacherRepository.findAll().stream().map(teacherMapper::mapToDTO).collect(Collectors.toSet());
    }

    @Override
    public Set<GroupDTO> findAllGroupsWithTeacherId(int id) throws SQLException {
        return teacherRepository.findById(id).getGroups().stream().map(groupMapper::mapToDTO).collect(Collectors.toSet());
    }

    @Override
    public Set<SubjectDTO> findAllSubjectsWithTeacherId(int id) throws SQLException {
        return teacherRepository.findById(id).getSubjects().stream().map(subjectMapper::mapToDTO).collect(Collectors.toSet());
    }

    @Override
    public Set<ExamDTO> findAllExamsWithTeacherId(int id) throws SQLException {
        return teacherRepository.findById(id).getExams().stream().map(examMapper::mapToDTO).collect(Collectors.toSet());
    }
}