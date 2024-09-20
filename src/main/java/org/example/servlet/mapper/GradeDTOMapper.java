package org.example.servlet.mapper;

import org.example.model.GradeEntity;
import org.example.model.GroupEntity;
import org.example.servlet.dto.GradeDTO;
import org.example.servlet.dto.GroupDTO;

public interface GradeDTOMapper {
    GradeEntity mapToEntity(GradeDTO gradeDTO);

    GradeEntity mapToEntity(GradeDTO gradeDTO, int id);

    GradeDTO mapToDTO(GradeEntity grade);
}