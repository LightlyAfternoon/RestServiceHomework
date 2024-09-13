package org.example.servlet.mapper;

import org.example.model.StudentEntity;
import org.example.servlet.dto.StudentDTO;

public interface StudentDTOMapper {
    StudentEntity mapToEntity(StudentDTO studentDTO);

    StudentDTO mapToDTO(StudentEntity student);
}