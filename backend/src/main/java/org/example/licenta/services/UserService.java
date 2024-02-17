package org.example.licenta.services;

import org.example.licenta.db.entities.UserEntity;
import org.example.licenta.db.entities.enums.UserRole;
import org.example.licenta.db.repositories.UserRepository;
import org.example.licenta.dto.UserDto;
import org.example.licenta.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<UserDto> getUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

//    TODO: Implement the exception handling
    public UserDto getUserById(String id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return userMapper.toDto(user.get());
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public void createUser(UserDto newUser) {
//        UserEntity userEntity = userMapper.toEntity(newUser);
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("test");
        userEntity.setUserFirstName("test");
        userEntity.setUserEmail("test");
        userEntity.setUserPassword("test");
        userEntity.setUserRole(UserRole.ADMIN);
        userEntity.setUserId("test");
        userEntity.setTeamEntity(null);
        userRepository.save(userEntity);
    }

    public void updateUser(String id, UserDto userDto) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        user.get().setUserName(userDto.getUserName());
//        user.get().setUserFirstName(userDto.getUserFirstName());
//        user.get().setUserEmail(userDto.getUserEmail());
//        user.get().setUserPassword(userDto.getUserPassword());
//        user.get().setUserRole(userDto.getUserRole());
//        TODO: Implement the team update
//        user.get().setTeamEntity(userDto.getTeamEntity());
        userRepository.save(user.get());
    }
}
