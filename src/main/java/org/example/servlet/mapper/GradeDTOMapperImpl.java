package org.example.servlet.mapper;

import org.example.model.GradeEntity;
import org.example.servlet.dto.GradeDTO;

public class GradeDTOMapperImpl implements GradeDTOMapper {
    @Override
    public GradeEntity mapToEntity(GradeDTO gradeDTO) {
        if (gradeDTO == null) {
            return null;
        }

        return new GradeEntity(
                gradeDTO.getStudentId(),
                gradeDTO.getExamId(),
                gradeDTO.getMark()
        );
    }

    @Override
    public GradeEntity mapToEntity(GradeDTO gradeDTO, int id) {
        if (gradeDTO == null) {
            return null;
        }

        return new GradeEntity(
                id,
                gradeDTO.getStudentId(),
                gradeDTO.getExamId(),
                gradeDTO.getMark()
        );
    }

    @Override
    public GradeDTO mapToDTO(GradeEntity grade) {
        if (grade == null) {
            return null;
        }

        return new GradeDTO(
                grade.getId(),
                grade.getStudentId(),
                grade.getExamId(),
                grade.getMark()
        );
    }
}