package org.example.licenta.mappers;

import org.example.licenta.db.entities.UserEntity;
import org.example.licenta.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId")
    @Mapping(target = "userName")
    @Mapping(target = "userFirstName")
    @Mapping(target = "userEmail")
    @Mapping(target = "userPassword")
    @Mapping(target = "userRole")
    UserDto toDto(UserEntity userEntity);

    @Mapping(target = "userId")
    @Mapping(target = "userName")
    @Mapping(target = "userFirstName")
    @Mapping(target = "userEmail")
    @Mapping(target = "userPassword")
    @Mapping(target = "userRole")
    UserEntity toEntity(UserDto userDto);
}