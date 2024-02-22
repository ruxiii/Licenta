package org.example.licenta.mappers;

import org.example.licenta.db.entities.AuthenticationEntity;
import org.example.licenta.db.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {

    @Mapping(target = "userId")
    @Mapping(target = "userPassword")
    @Mapping(target = "userRole")
    AuthenticationEntity registered(UserEntity userEntity);
}
