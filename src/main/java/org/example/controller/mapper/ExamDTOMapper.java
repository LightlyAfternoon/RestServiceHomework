package org.example.controller.mapper;

import org.example.model.ExamEntity;
import org.example.controller.dto.ExamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExamDTOMapper {
    ExamDTOMapper INSTANCE = Mappers.getMapper(ExamDTOMapper.class);

    @Mapping(target = "id", ignore = true)
    ExamEntity mapToEntity(ExamDTO examDTO);

    @Mapping(target = "id", expression = "java(id)")
    ExamEntity mapToEntity(ExamDTO examDTO, int id);

    ExamDTO mapToDTO(ExamEntity exam);
}