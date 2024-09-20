package org.example.servlet.mapper;

import org.example.model.StudentEntity;
import org.example.servlet.dto.StudentDTO;

public class StudentDTOMapperImpl implements StudentDTOMapper {
    @Override
    public StudentEntity mapToEntity(StudentDTO studentDTO) {
        if (studentDTO == null) {
            return null;
        }

        return new StudentEntity(
                studentDTO.getFirstName(),
                studentDTO.getLastName(),
                studentDTO.getPatronymic(),
                studentDTO.getGroupId()
        );
    }

    @Override
    public StudentEntity mapToEntity(StudentDTO studentDTO, int id) {
        if (studentDTO == null) {
            return null;
        }

        return new StudentEntity(
                id,
                studentDTO.getFirstName(),
                studentDTO.getLastName(),
                studentDTO.getPatronymic(),
                studentDTO.getGroupId()
        );
    }

    @Override
    public StudentDTO mapToDTO(StudentEntity student) {
        if (student == null) {
            return null;
        }

        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getPatronymic(),
                student.getGroupId()
        );
    }
}