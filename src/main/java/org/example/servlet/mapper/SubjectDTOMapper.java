package org.example.servlet.mapper;

import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.servlet.dto.SubjectDTO;
import org.example.servlet.dto.TeacherDTO;

public interface SubjectDTOMapper {
    SubjectEntity mapToEntity(SubjectDTO subjectDTO);

    SubjectEntity mapToEntity(SubjectDTO subjectDTO, int id);

    SubjectDTO mapToDTO(SubjectEntity subject);
}