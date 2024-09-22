package org.example.service.impl;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.repository.SubjectRepository;
import org.example.repository.impl.SubjectRepositoryImpl;
import org.example.service.SubjectService;

import java.sql.SQLException;
import java.util.List;

public class SubjectServiceImpl implements SubjectService {
    SubjectRepository subjectRepository;

    public SubjectServiceImpl() {
        this.subjectRepository = new SubjectRepositoryImpl();
    }

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public SubjectEntity findById(int id) throws SQLException {
        return subjectRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return subjectRepository.deleteById(id);
    }

    @Override
    public SubjectEntity save(SubjectEntity entity) throws SQLException {
        return subjectRepository.save(entity);
    }

    @Override
    public List<SubjectEntity> findAll() throws SQLException {
        return subjectRepository.findAll();
    }

    @Override
    public List<TeacherEntity> findAllTeachersWithSubjectId(int id) throws SQLException {
        return subjectRepository.findAllTeachersWithSubjectId(id);
    }

    @Override
    public List<GroupEntity> findAllGroupsWithSubjectId(int id) throws SQLException {
        return subjectRepository.findAllGroupsWithSubjectId(id);
    }

    @Override
    public List<ExamEntity> findAllExamsWithSubjectId(int id) throws SQLException {
        return subjectRepository.findAllExamsWithSubjectId(id);
    }

    @Override
    public TeacherEntity save(SubjectEntity subject, TeacherEntity teacher) throws SQLException {
        return subjectRepository.save(subject, teacher);
    }

    @Override
    public GroupEntity save(SubjectEntity subject, GroupEntity group) throws SQLException {
        return subjectRepository.save(subject, group);
    }
}