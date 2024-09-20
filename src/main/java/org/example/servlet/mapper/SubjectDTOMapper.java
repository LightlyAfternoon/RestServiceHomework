package org.example.servlet.mapper;

import org.example.model.SubjectEntity;
import org.example.servlet.dto.SubjectDTO;

public interface SubjectDTOMapper {
    SubjectEntity mapToEntity(SubjectDTO subjectDTO);

    SubjectEntity mapToEntity(SubjectDTO subjectDTO, int id);

    SubjectDTO mapToDTO(SubjectEntity subject);
}