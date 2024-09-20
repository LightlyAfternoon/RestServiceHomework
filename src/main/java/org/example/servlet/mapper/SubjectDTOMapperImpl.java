package org.example.servlet.mapper;

import org.example.model.SubjectEntity;
import org.example.servlet.dto.SubjectDTO;

public class SubjectDTOMapperImpl implements SubjectDTOMapper {
    @Override
    public SubjectEntity mapToEntity(SubjectDTO subjectDTO) {
        if (subjectDTO == null) {
            return null;
        }

        return new SubjectEntity(
                subjectDTO.getName()
        );
    }

    @Override
    public SubjectDTO mapToDTO(SubjectEntity subject) {
        if (subject == null) {
            return null;
        }

        return new SubjectDTO(
                subject.getId(),
                subject.getName()
        );
    }
}