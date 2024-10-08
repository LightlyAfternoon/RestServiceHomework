package org.example.servlet.mapper;

import org.example.model.GroupEntity;
import org.example.servlet.dto.GroupDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupDTOMapper {
    GroupDTOMapper INSTANCE = Mappers.getMapper(GroupDTOMapper.class);

    @Mapping(target = "id", ignore = true)
    GroupEntity mapToEntity(GroupDTO groupDTO);

    @Mapping(target = "id", expression = "java(id)")
    GroupEntity mapToEntity(GroupDTO groupDTO, int id);

    GroupDTO mapToDTO(GroupEntity group);
}