package org.example.licenta.services;

import org.example.licenta.db.entities.AuthenticationEntity;
import org.example.licenta.db.entities.TeamEntity;
import org.example.licenta.db.entities.UserEntity;
import org.example.licenta.db.entities.enums.UserRoles;
import org.example.licenta.db.repositories.AuthenticationRepository;
import org.example.licenta.db.repositories.TeamRepository;
import org.example.licenta.db.repositories.UserRepository;
import org.example.licenta.dto.UserDto;
import org.example.licenta.exceptions.TeamNotFoundException;
import org.example.licenta.exceptions.UserAlreadyExistsException;
import org.example.licenta.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationRepository authRepository;

    @Autowired
    private TeamRepository teamRepository;

    public List<UserDto> getUsers() throws UserNotFoundException {
        if (userRepository.findAll().isEmpty()) {
            throw new UserNotFoundException("No users found");
        }
        else {
            List<UserEntity> users = userRepository.findAll();
            List<UserDto> userDtos = new ArrayList<>();
            for (UserEntity userEntity : users) {
                TeamEntity teamEntity = userEntity.getTeamEntity();
                String teamId = teamEntity.getTeamId();

                UserDto userDto = new UserDto();
                userDto.setUserName(userEntity.getUserName());
                userDto.setUserFirstName(userEntity.getUserFirstName());
                userDto.setUserEmail(userEntity.getUserEmail());
                userDto.setUserPassword(userEntity.getUserPassword());
                userDto.setUserRole(userEntity.getUserRole().toString());
                userDto.setTeamId(teamId);
                userDtos.add(userDto);
            }
            return userDtos;
        }
    }

    public UserDto getUserById(String id) throws UserNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        else{
            UserEntity userEntity = user.get();
            TeamEntity teamEntity = userEntity.getTeamEntity();
            String teamId = teamEntity.getTeamId();

            UserDto userDto = new UserDto();
            userDto.setUserName(userEntity.getUserName());
            userDto.setUserFirstName(userEntity.getUserFirstName());
            userDto.setUserEmail(userEntity.getUserEmail());
            userDto.setUserPassword(userEntity.getUserPassword());
            userDto.setUserRole(userEntity.getUserRole().toString());
            userDto.setTeamId(teamId);
            return userDto;
        }
    }

    public void deleteUser(String id) throws UserNotFoundException {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        else {
            userRepository.deleteById(id);
            authRepository.deleteById(id);
        }
    }

    private Integer getPosition(String str) {
        int position = 0;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                position = i;
                break;
            }
        }
        return position;
    }

    private ArrayList<Integer> getTheNumber(String str) {
        int number = 0;
        int ok = -1;
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                if (ok < 0) {
                    ok = i;
                    result.add(ok);
                }
                number = number * 10 + (str.charAt(i) - '0');
            }
        }
        result.add(number);
        return result;
    }

    private Integer createId(List<String> userIds) {
        int max = 0;
        for (String userId : userIds) {
            ArrayList<Integer> number = getTheNumber(userId);
            if (number.get(1) > max) {
                max = number.get(1);
            }
        }
        return max;
    }

    public void createUser(UserDto userDto) throws UserAlreadyExistsException, TeamNotFoundException {
        if (!teamRepository.existsById(userDto.getTeamId())) {
            throw new TeamNotFoundException("Team not found");
        } else {
            List<UserEntity> users = userRepository.findAll();
            List<String> userIds = new ArrayList<>();
            for (UserEntity user : users) {
                userIds.add(user.getUserId());
            }
            int max = createId(userIds);
            int position = getPosition(userIds.get(0));

            String userId = userIds.get(0).substring(0, position) + (max + 1);

            TeamEntity teamEntity = teamRepository.findById(userDto.getTeamId()).get();
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(userId);
            userEntity.setUserName(userDto.getUserName());
            userEntity.setUserFirstName(userDto.getUserFirstName());
            userEntity.setUserEmail(userDto.getUserEmail());
            // TODO: Encode the password
            userEntity.setUserPassword(userDto.getUserPassword());
            userEntity.setUserRole(UserRoles.valueOf(userDto.getUserRole()));
            userEntity.setTeamEntity(teamEntity);

            userRepository.save(userEntity);

            AuthenticationEntity authEntity = new AuthenticationEntity();
            authEntity.setUserId(userEntity.getUserId());
            authEntity.setUserPassword(userEntity.getUserPassword());
            authEntity.setUserRole(userEntity.getUserRole().toString());
            authRepository.save(authEntity);
        }
    }


    public UserDto updateUser(UserDto userDto, String id) throws UserNotFoundException, TeamNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        else {
            if (!teamRepository.existsById(userDto.getTeamId())) {
                throw new TeamNotFoundException("Team not found");
            }
            else {
                String teamId = userDto.getTeamId();
                TeamEntity teamEntity = teamRepository.findById(teamId).get();
                UserEntity userEntity = user.get();
                userEntity.setUserName(userDto.getUserName());
                userEntity.setUserFirstName(userDto.getUserFirstName());
                userEntity.setUserEmail(userDto.getUserEmail());
                userEntity.setUserPassword(userDto.getUserPassword());
                userEntity.setUserRole(UserRoles.valueOf(userDto.getUserRole()));
                userEntity.setTeamEntity(teamEntity);

                UserDto updatedUser = new UserDto();
                updatedUser.setUserName(userEntity.getUserName());
                updatedUser.setUserFirstName(userEntity.getUserFirstName());
                updatedUser.setUserEmail(userEntity.getUserEmail());
                updatedUser.setUserPassword(userEntity.getUserPassword());
                updatedUser.setUserRole(userEntity.getUserRole().toString());
                updatedUser.setTeamId(teamEntity.getTeamId());

                userRepository.save(userEntity);

                AuthenticationEntity authEntity = new AuthenticationEntity();
                authEntity.setUserId(userEntity.getUserId());
                authEntity.setUserPassword(userEntity.getUserPassword());
                authEntity.setUserRole(userEntity.getUserRole().toString());
                authRepository.save(authEntity);
                return updatedUser;
            }
        }
    }
}
