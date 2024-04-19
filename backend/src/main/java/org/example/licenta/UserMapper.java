package org.example.licenta;

import org.example.licenta.db.entities.UserEntity;
import org.example.licenta.dto.UserFullDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserFullDto toFullDto(UserEntity userEntity);
}
