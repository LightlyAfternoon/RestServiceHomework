package org.example.repository.mapper;

import org.example.model.SubjectEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SubjectResultSetMapper {
    SubjectEntity map(ResultSet resultSet) throws SQLException;
}