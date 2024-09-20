package org.example.servlet.mapper;

import org.example.model.ExamEntity;
import org.example.servlet.dto.ExamDTO;

public interface ExamDTOMapper {
    ExamEntity mapToEntity(ExamDTO examDTO);

    ExamEntity mapToEntity(ExamDTO examDTO, int id);

    ExamDTO mapToDTO(ExamEntity exam);
}