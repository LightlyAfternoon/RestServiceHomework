package org.example.service.impl;

import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.StudentEntity;
import org.example.model.SubjectEntity;
import org.example.repository.GroupRepository;
import org.example.repository.impl.GroupRepositoryImpl;
import org.example.service.GroupService;

import java.sql.SQLException;
import java.util.List;

public class GroupServiceImpl implements GroupService {
    GroupRepository groupRepository;

    public GroupServiceImpl() {
        groupRepository = new GroupRepositoryImpl();
    }

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public GroupServiceImpl(String path) {
        this.groupRepository = new GroupRepositoryImpl(path);
    }

    @Override
    public GroupEntity findById(int id) throws SQLException {
        return groupRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return groupRepository.deleteById(id);
    }

    @Override
    public GroupEntity save(GroupEntity entity) throws SQLException {
        return groupRepository.save(entity);
    }

    @Override
    public List<GroupEntity> findAll() throws SQLException {
        return groupRepository.findAll();
    }

    @Override
    public List<StudentEntity> findAllStudentsWithGroupId(int id) throws SQLException {
        return groupRepository.findAllStudentsWithGroupId(id);
    }

    @Override
    public List<ExamEntity> findAllExamsWithGroupId(int id) throws SQLException {
        return groupRepository.findAllExamsWithGroupId(id);
    }

    @Override
    public List<SubjectEntity> findAllSubjectsWithGroupId(int id) throws SQLException {
        return groupRepository.findAllSubjectsWithGroupId(id);
    }
}