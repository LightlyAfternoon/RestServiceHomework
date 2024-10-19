package org.example.controller.mapper;

import org.example.model.SubjectEntity;
import org.example.controller.dto.SubjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SubjectDTOMapper {
    SubjectDTOMapper INSTANCE = Mappers.getMapper(SubjectDTOMapper.class);

    @Mapping(target = "id", ignore = true)
    SubjectEntity mapToEntity(SubjectDTO subjectDTO);

    @Mapping(target = "id", expression = "java(id)")
    SubjectEntity mapToEntity(SubjectDTO subjectDTO, int id);

    SubjectDTO mapToDTO(SubjectEntity subject);
}