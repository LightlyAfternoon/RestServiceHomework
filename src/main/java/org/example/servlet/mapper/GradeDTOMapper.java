package org.example.servlet.mapper;

import org.example.model.GradeEntity;
import org.example.servlet.dto.GradeDTO;

public interface GradeDTOMapper {
    GradeEntity mapToEntity(GradeDTO gradeDTO);

    GradeDTO mapToDTO(GradeEntity grade);
}