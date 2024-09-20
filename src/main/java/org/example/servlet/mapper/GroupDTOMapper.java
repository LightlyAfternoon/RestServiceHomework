package org.example.servlet.mapper;

import org.example.model.GroupEntity;
import org.example.model.StudentEntity;
import org.example.servlet.dto.GroupDTO;
import org.example.servlet.dto.StudentDTO;

public interface GroupDTOMapper {
    GroupEntity mapToEntity(GroupDTO groupDTO);

    GroupEntity mapToEntity(GroupDTO groupDTO, int id);

    GroupDTO mapToDTO(GroupEntity group);
}