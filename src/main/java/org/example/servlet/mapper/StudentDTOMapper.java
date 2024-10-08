package org.example.servlet.mapper;

import org.example.model.StudentEntity;
import org.example.servlet.dto.StudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentDTOMapper {
    StudentDTOMapper INSTANCE = Mappers.getMapper(StudentDTOMapper.class);

    @Mapping(target = "id", ignore = true)
    StudentEntity mapToEntity(StudentDTO studentDTO);

    @Mapping(target = "id", expression = "java(id)")
    StudentEntity mapToEntity(StudentDTO studentDTO, int id);

    StudentDTO mapToDTO(StudentEntity student);
}