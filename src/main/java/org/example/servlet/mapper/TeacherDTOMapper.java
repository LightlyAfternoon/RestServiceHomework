package org.example.servlet.mapper;

import org.example.model.TeacherEntity;
import org.example.servlet.dto.TeacherDTO;

public interface TeacherDTOMapper {
    TeacherEntity mapToEntity(TeacherDTO teacherDTO);

    TeacherDTO mapToDTO(TeacherEntity teacher);
}