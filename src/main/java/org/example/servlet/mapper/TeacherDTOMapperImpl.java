package org.example.servlet.mapper;

import org.example.model.TeacherEntity;
import org.example.servlet.dto.TeacherDTO;

public class TeacherDTOMapperImpl implements TeacherDTOMapper {
    @Override
    public TeacherEntity mapToEntity(TeacherDTO teacherDTO) {
        if (teacherDTO == null) {
            return null;
        }

        return new TeacherEntity (
                teacherDTO.getFirstName(),
                teacherDTO.getLastName(),
                teacherDTO.getPatronymic()
        );
    }

    @Override
    public TeacherDTO mapToDTO(TeacherEntity teacher) {
        if (teacher == null) {
            return null;
        }
        return new TeacherDTO (
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getPatronymic()
        );
    }
}