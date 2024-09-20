package org.example.servlet.mapper;

import org.example.model.GroupEntity;
import org.example.servlet.dto.GroupDTO;

public class GroupDTOMapperImpl implements GroupDTOMapper {
    @Override
    public GroupEntity mapToEntity(GroupDTO groupDTO) {
        if (groupDTO == null) {
            return null;
        }

        return new GroupEntity(
                groupDTO.getName(),
                groupDTO.getStartDate(),
                groupDTO.getEndDate(),
                groupDTO.getTeacherId()
        );
    }

    @Override
    public GroupDTO mapToDTO(GroupEntity group) {
        if (group == null) {
            return null;
        }

        return new GroupDTO(
                group.getId(),
                group.getName(),
                group.getStartDate(),
                group.getEndDate(),
                group.getTeacherId()
        );
    }
}
