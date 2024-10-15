package org.example.servlet.mapper;

import org.example.model.GradeEntity;
import org.example.servlet.dto.GradeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GradeDTOMapper {
    GradeDTOMapper INSTANCE = Mappers.getMapper(GradeDTOMapper.class);

    @Mapping(target = "id", ignore = true)
    GradeEntity mapToEntity(GradeDTO gradeDTO);

    @Mapping(target = "id", expression = "java(id)")
    GradeEntity mapToEntity(GradeDTO gradeDTO, int id);

    GradeDTO mapToDTO(GradeEntity grade);
}