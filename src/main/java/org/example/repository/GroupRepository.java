package org.example.repository;

import org.example.model.GroupEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Set;

@org.springframework.stereotype.Repository
public interface GroupRepository extends Repository<GroupEntity, Integer> {
    @Transactional
    GroupEntity findById(int id) throws SQLException;

    @Transactional
    @Modifying
    boolean deleteById(int id) throws SQLException;

    @Transactional
    @Modifying
    GroupEntity save(GroupEntity entity) throws SQLException;

    @Transactional
    Set<GroupEntity> findAll() throws SQLException;
}