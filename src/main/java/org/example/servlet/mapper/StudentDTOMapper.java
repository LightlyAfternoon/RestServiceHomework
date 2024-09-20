package org.example.servlet.mapper;

import org.example.model.StudentEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.servlet.dto.StudentDTO;
import org.example.servlet.dto.SubjectDTO;
import org.example.servlet.dto.TeacherDTO;

public interface StudentDTOMapper {
    StudentEntity mapToEntity(StudentDTO studentDTO);

    StudentEntity mapToEntity(StudentDTO studentDTO, int id);

    StudentDTO mapToDTO(StudentEntity student);
}