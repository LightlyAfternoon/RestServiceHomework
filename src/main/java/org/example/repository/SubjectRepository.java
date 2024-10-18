package org.example.repository;

import org.example.model.SubjectEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Set;

@org.springframework.stereotype.Repository
public interface SubjectRepository extends Repository<SubjectEntity, Integer> {
    @Transactional
    SubjectEntity findById(int id) throws SQLException;

    @Modifying
    @Transactional
    void deleteById(int id);

    @Modifying
    @Transactional
    SubjectEntity save(SubjectEntity entity) throws SQLException;

    @Transactional
    Set<SubjectEntity> findAll() throws SQLException;
}