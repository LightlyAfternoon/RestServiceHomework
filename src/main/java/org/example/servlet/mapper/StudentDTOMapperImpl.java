package org.example.servlet.mapper;

import org.example.model.StudentEntity;
import org.example.servlet.dto.StudentDTO;

public class StudentDTOMapperImpl implements StudentDTOMapper {
    @Override
    public StudentEntity mapToEntity(StudentDTO studentDTO) {
        return new StudentEntity(
                studentDTO.getFirstName(),
                studentDTO.getLastName(),
                studentDTO.getPatronymic(),
                studentDTO.getGroupId()
        );
    }

    @Override
    public StudentDTO mapToDTO(StudentEntity student) {
        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getPatronymic(),
                student.getGroupId()
        );
    }
}