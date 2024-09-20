package org.example.servlet.mapper;

import org.example.model.TeacherEntity;
import org.example.servlet.dto.TeacherDTO;

public interface TeacherDTOMapper {
    TeacherEntity mapToEntity(TeacherDTO teacherDTO);

    TeacherEntity mapToEntity(TeacherDTO teacherDTO, int id);

    TeacherDTO mapToDTO(TeacherEntity teacher);
}