package org.example.servlet.mapper;

import org.example.model.SubjectEntity;
import org.example.servlet.dto.SubjectDTO;

public class SubjectDTOMapperImpl implements SubjectDTOMapper {
    @Override
    public SubjectEntity mapToEntity(SubjectDTO subjectDTO) {
        return new SubjectEntity(
                subjectDTO.getName()
        );
    }

    @Override
    public SubjectDTO mapToDTO(SubjectEntity subject) {
        return new SubjectDTO(
                subject.getId(),
                subject.getName()
        );
    }
}