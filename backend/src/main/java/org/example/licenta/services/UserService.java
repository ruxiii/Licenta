package org.example.licenta.services;

import org.example.licenta.db.entities.AuthenticationEntity;
import org.example.licenta.db.entities.UserEntity;
import org.example.licenta.db.entities.enums.UserRoles;
import org.example.licenta.db.repositories.AuthenticationRepository;
import org.example.licenta.db.repositories.UserRepository;
import org.example.licenta.dto.UserDto;
import org.example.licenta.exceptions.UserNotFoundException;
import org.example.licenta.mappers.AuthenticationMapper;
import org.example.licenta.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserMapper userMapper;

    private final AuthenticationMapper authMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationRepository authRepository;

    public UserService(UserMapper userMapper, AuthenticationMapper authMapper) {
        this.userMapper = userMapper;
        this.authMapper = authMapper;
    }

    public List<UserDto> getUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    public UserDto getUserById(String id) throws UserNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
                throw new UserNotFoundException("User not found");
        }
        return userMapper.toDto(user.get());
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
        authRepository.deleteById(id);
    }

    public void createUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDto.getUserId());
        userEntity.setUserName(userDto.getUserName());
        userEntity.setUserFirstName(userDto.getUserFirstName());
        userEntity.setUserEmail(userDto.getUserEmail());
//        TODO: Encode the password
        userEntity.setUserPassword(userDto.getUserPassword());
        userEntity.setUserRole(UserRoles.valueOf(userDto.getUserRole()));

//        TODO: nu merge ca-i fk
//        userEntity.setTeamEntity(userEntity.setTeamId("CAL"));
//
        userRepository.save(userEntity);
        authRepository.save(authMapper.registered(userEntity));
    }

    public UserDto updateUser(UserDto userDto, String id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        user.get().setUserName(userDto.getUserName());
        user.get().setUserFirstName(userDto.getUserFirstName());
        user.get().setUserEmail(userDto.getUserEmail());
        user.get().setUserPassword(userDto.getUserPassword());
        user.get().setUserRole(UserRoles.valueOf(userDto.getUserRole()));
//        TODO: Implement the team update
//        user.get().setTeamEntity(userDto.getTeamEntity());
//
        userRepository.save(user.get());
        authRepository.save(authMapper.registered(user.get()));
        return userMapper.toDto(user.get());
    }
}
