package org.example.servlet.mapper;

import org.example.model.ExamEntity;
import org.example.servlet.dto.ExamDTO;

public class ExamDTOMapperImpl implements ExamDTOMapper {
    @Override
    public ExamEntity mapToEntity(ExamDTO examDTO) {
        if (examDTO == null) {
            return null;
        }

        return new ExamEntity(
                examDTO.getStartDate(),
                examDTO.getGroupId(),
                examDTO.getSubjectTeacherId()
        );
    }

    @Override
    public ExamEntity mapToEntity(ExamDTO examDTO, int id) {

        if (examDTO == null) {
            return null;
        }

        return new ExamEntity(
                id,
                examDTO.getStartDate(),
                examDTO.getGroupId(),
                examDTO.getSubjectTeacherId()
        );
    }

    @Override
    public ExamDTO mapToDTO(ExamEntity exam) {
        if (exam == null) {
            return null;
        }

        return new ExamDTO(
                exam.getId(),
                exam.getStartDate(),
                exam.getGroupId(),
                exam.getSubjectTeacherId()
        );
    }
}