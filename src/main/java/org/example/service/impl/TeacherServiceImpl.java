package org.example.service.impl;

import org.example.model.TeacherEntity;
import org.example.repository.TeacherRepository;
import org.example.repository.impl.TeacherRepositoryImpl;
import org.example.service.TeacherService;
import org.example.servlet.dto.ExamDTO;
import org.example.servlet.dto.GroupDTO;
import org.example.servlet.dto.SubjectDTO;
import org.example.servlet.dto.TeacherDTO;
import org.example.servlet.mapper.ExamDTOMapper;
import org.example.servlet.mapper.GroupDTOMapper;
import org.example.servlet.mapper.SubjectDTOMapper;
import org.example.servlet.mapper.TeacherDTOMapper;

import java.sql.SQLException;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {
    TeacherRepository teacherRepository;

    TeacherDTOMapper teacherMapper = TeacherDTOMapper.INSTANCE;
    GroupDTOMapper groupMapper = GroupDTOMapper.INSTANCE;
    SubjectDTOMapper subjectMapper = SubjectDTOMapper.INSTANCE;
    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;

    public TeacherServiceImpl() {
        this.teacherRepository = new TeacherRepositoryImpl();
    }

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public TeacherDTO findById(int id) throws SQLException {
        return teacherMapper.mapToDTO(teacherRepository.findById(id));
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return teacherRepository.deleteById(id);
    }

    @Override
    public TeacherDTO save(TeacherEntity entity) throws SQLException {
        return teacherMapper.mapToDTO(teacherRepository.save(entity));
    }

    @Override
    public List<TeacherDTO> findAll() throws SQLException {
        return teacherRepository.findAll().stream().map(teacherMapper::mapToDTO).toList();
    }

    @Override
    public List<GroupDTO> findAllGroupsWithTeacherId(int id) throws SQLException {
        return teacherRepository.findAllGroupsWithTeacherId(id).stream().map(groupMapper::mapToDTO).toList();
    }

    @Override
    public List<SubjectDTO> findAllSubjectsWithTeacherId(int id) throws SQLException {
        return teacherRepository.findAllSubjectsWithTeacherId(id).stream().map(subjectMapper::mapToDTO).toList();
    }

    @Override
    public List<ExamDTO> findAllExamsWithTeacherId(int id) throws SQLException {
        return teacherRepository.findAllExamsWithTeacherId(id).stream().map(examMapper::mapToDTO).toList();
    }
}