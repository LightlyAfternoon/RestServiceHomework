package org.example.servlet.mapper;

import org.example.model.ExamEntity;
import org.example.servlet.dto.ExamDTO;

public class ExamDTOMapperImpl implements ExamDTOMapper {
    @Override
    public ExamEntity mapToEntity(ExamDTO examDTO) {
        return new ExamEntity(
                examDTO.getStartDate(),
                examDTO.getGroupId(),
                examDTO.getSubjectTeacherId()
        );
    }

    @Override
    public ExamDTO mapToDTO(ExamEntity exam) {
        return new ExamDTO(
                exam.getId(),
                exam.getStartDate(),
                exam.getGroupId(),
                exam.getSubjectTeacherId()
        );
    }
}