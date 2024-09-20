package org.example.servlet.mapper;

import org.example.model.StudentEntity;
import org.example.servlet.dto.StudentDTO;

public interface StudentDTOMapper {
    StudentEntity mapToEntity(StudentDTO studentDTO);

    StudentEntity mapToEntity(StudentDTO studentDTO, int id);

    StudentDTO mapToDTO(StudentEntity student);
}