package org.example.service.impl;

import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
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
import java.util.List;

public class SubjectServiceImpl implements SubjectService {
    SubjectRepository subjectRepository;

    SubjectDTOMapper subjectMapper = SubjectDTOMapper.INSTANCE;
    TeacherDTOMapper teacherMapper = TeacherDTOMapper.INSTANCE;
    GroupDTOMapper groupMapper = GroupDTOMapper.INSTANCE;
    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;

    public SubjectServiceImpl() {
    }

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
    public SubjectDTO save(SubjectEntity entity) throws SQLException {
        return subjectMapper.mapToDTO(subjectRepository.save(entity));
    }

    @Override
    public List<SubjectDTO> findAll() throws SQLException {
        return subjectRepository.findAll().stream().map(subjectMapper::mapToDTO).toList();
    }

    @Override
    public List<TeacherDTO> findAllTeachersWithSubjectId(int id) throws SQLException {
        return subjectRepository.findAllTeachersWithSubjectId(id).stream().map(teacherMapper::mapToDTO).toList();
    }

    @Override
    public List<GroupDTO> findAllGroupsWithSubjectId(int id) throws SQLException {
        return subjectRepository.findAllGroupsWithSubjectId(id).stream().map(groupMapper::mapToDTO).toList();
    }

    @Override
    public List<ExamDTO> findAllExamsWithSubjectId(int id) throws SQLException {
        return subjectRepository.findAllExamsWithSubjectId(id).stream().map(examMapper::mapToDTO).toList();
    }

    @Override
    public TeacherDTO save(SubjectEntity subject, TeacherEntity teacher) throws SQLException {
        return teacherMapper.mapToDTO(subjectRepository.save(subject, teacher));
    }

    @Override
    public GroupDTO save(SubjectEntity subject, GroupEntity group) throws SQLException {
        return groupMapper.mapToDTO(subjectRepository.save(subject, group));
    }
}