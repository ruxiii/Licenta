package org.example.licenta.mappers;

import org.example.licenta.db.entities.DepartmentEntity;
import org.example.licenta.dto.DepartmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    @Mapping(target = "mapDto", ignore = true)
    @Mapping(target = "teams", ignore = true)
    DepartmentDto toDto(DepartmentEntity departmentEntity);

    DepartmentEntity toEntity(DepartmentDto departmentDto);

}
