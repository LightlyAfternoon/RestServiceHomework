package org.example.controller.mapper;

import org.example.model.TeacherEntity;
import org.example.controller.dto.TeacherDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TeacherDTOMapper {
    TeacherDTOMapper INSTANCE = Mappers.getMapper(TeacherDTOMapper.class);

    @Mapping(target = "id", ignore = true)
    TeacherEntity mapToEntity(TeacherDTO teacherDTO);

    @Mapping(target = "id", expression = "java(id)")
    TeacherEntity mapToEntity(TeacherDTO teacherDTO, int id);

    TeacherDTO mapToDTO(TeacherEntity teacher);
}