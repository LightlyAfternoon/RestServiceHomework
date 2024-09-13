package org.example.servlet.mapper;

import org.example.model.TeacherEntity;
import org.example.servlet.dto.TeacherDTO;

public class TeacherDTOMapperImpl implements TeacherDTOMapper {
    @Override
    public TeacherEntity mapToEntity(TeacherDTO teacherDTO) {
        return new TeacherEntity (
                teacherDTO.getFirstName(),
                teacherDTO.getLastName(),
                teacherDTO.getPatronymic()
        );
    }

    @Override
    public TeacherDTO mapToDTO(TeacherEntity teacher) {
        return new TeacherDTO (
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getPatronymic()
        );
    }
}