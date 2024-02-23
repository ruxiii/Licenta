package org.example.licenta.mappers;

import org.example.licenta.db.entities.DepartmentEntity;
import org.example.licenta.db.entities.TeamEntity;
import org.example.licenta.dto.TeamDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    TeamDto toDto(TeamEntity teamEntity);

//    String toDepartmentId(DepartmentEntity departmentEntity);
//
//    DepartmentEntity toDepartmentEntity(String departmentId);

    TeamEntity toEntity(TeamDto teamDto);
}
