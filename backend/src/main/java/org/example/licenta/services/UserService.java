package org.example.licenta.services;

import lombok.RequiredArgsConstructor;
import org.example.licenta.db.entities.AuthenticationEntity;
import org.example.licenta.db.entities.RoleEntity;
import org.example.licenta.db.entities.TeamEntity;
import org.example.licenta.db.entities.UserEntity;
import org.example.licenta.db.repositories.AuthenticationRepository;
import org.example.licenta.db.repositories.RoleRepository;
import org.example.licenta.db.repositories.TeamRepository;
import org.example.licenta.db.repositories.UserRepository;
import org.example.licenta.dto.UserDto;
import org.example.licenta.dto.UserFullDto;
import org.example.licenta.exceptions.TeamNotFoundException;
import org.example.licenta.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationRepository authRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public List<UserFullDto> getUsers() throws UserNotFoundException {
        if (userRepository.findAll().isEmpty()) {
            throw new UserNotFoundException("No users found");
        }
        else {
            List<UserEntity> users = userRepository.findAll();
            List<UserFullDto> fullDtos = new ArrayList<>();
            for (UserEntity userEntity : users) {
                TeamEntity teamEntity = userEntity.getTeamEntity();
                String teamId = teamEntity.getTeamId();

                UserFullDto userFullDto = new UserFullDto();
                userFullDto.setUserId(userEntity.getUserId());
                userFullDto.setUserName(userEntity.getUserName());
                userFullDto.setUserFirstName(userEntity.getUserFirstName());
                userFullDto.setUserEmail(userEntity.getUserEmail());
                userFullDto.setUserPassword(userEntity.getUserPassword());
                userFullDto.setUserRole(userEntity.getUserRole().toString());
                userFullDto.setTeamId(teamId);
                fullDtos.add(userFullDto);
            }
            return fullDtos;
        }
    }

    public UserFullDto getUserById(String id) throws UserNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        else{
            UserEntity userEntity = user.get();
            TeamEntity teamEntity = userEntity.getTeamEntity();
            String teamId = teamEntity.getTeamId();

            UserFullDto userFullDto = new UserFullDto();
            userFullDto.setUserId(userEntity.getUserId());
            userFullDto.setUserName(userEntity.getUserName());
            userFullDto.setUserFirstName(userEntity.getUserFirstName());
            userFullDto.setUserEmail(userEntity.getUserEmail());
            userFullDto.setUserPassword(userEntity.getUserPassword());
            userFullDto.setUserRole(userEntity.getUserRole().toString());
            userFullDto.setTeamId(teamId);
            return userFullDto;
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

    public UserFullDto createUser(UserDto userDto) throws TeamNotFoundException {
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

            String password = passwordEncoder.encode(userDto.getUserPassword());

            TeamEntity teamEntity = teamRepository.findById(userDto.getTeamId()).get();
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(userId);
            userEntity.setUserName(userDto.getUserName().toUpperCase());
            userEntity.setUserFirstName(userDto.getUserFirstName().toUpperCase());
            userEntity.setUserEmail(userDto.getUserEmail());
            userEntity.setUserPassword(password);
            userEntity.setUserRole(roleRepository.findByAuthority("USER").get().getAuthority());
            userEntity.setTeamEntity(teamEntity);

            userRepository.save(userEntity);

            Set<RoleEntity> authorities = new HashSet<>();
            authorities.add(roleRepository.findByAuthority("USER").get());

            AuthenticationEntity authEntity = new AuthenticationEntity();
            authEntity.setUserId(userEntity.getUserId());
            authEntity.setUserPassword(password);
            authEntity.setAuthorities(authorities);
            authRepository.save(authEntity);

            Authentication auth = new UsernamePasswordAuthenticationToken(authEntity, null, authEntity.getAuthorities());
            String token = tokenService.generateJwt(auth);

            UserFullDto userFullDto = new UserFullDto();
            userFullDto.setUserId(userEntity.getUserId());
            userFullDto.setUserName(userEntity.getUserName());
            userFullDto.setUserFirstName(userEntity.getUserFirstName());
            userFullDto.setUserEmail(userEntity.getUserEmail());
            userFullDto.setUserPassword(userEntity.getUserPassword());
            userFullDto.setUserRole(userEntity.getUserRole());
            userFullDto.setTeamId(teamEntity.getTeamId());
            userFullDto.setToken(token);

            return userFullDto;
        }
    }
    public UserFullDto updateUser(UserFullDto userFullDto, String id) throws UserNotFoundException, TeamNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        AuthenticationEntity auth = authRepository.findById(id).get();
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        else {
            if (!teamRepository.existsById(userFullDto.getTeamId())) {
                throw new TeamNotFoundException("Team not found");
            }
            else {
                String teamId = userFullDto.getTeamId();
                TeamEntity teamEntity = teamRepository.findById(teamId).get();
                UserEntity userEntity = user.get();
                userEntity.setUserName(userFullDto.getUserName().toUpperCase());
                userEntity.setUserFirstName(userFullDto.getUserFirstName().toUpperCase());
                userEntity.setUserEmail(userFullDto.getUserEmail());
                userEntity.setUserPassword(userFullDto.getUserPassword());
                userEntity.setUserRole(roleRepository.findByAuthority("USER").get().getAuthority());
                userEntity.setTeamEntity(teamEntity);

                UserFullDto updatedUser = new UserFullDto();
                updatedUser.setUserName(userEntity.getUserName());
                updatedUser.setUserFirstName(userEntity.getUserFirstName());
                updatedUser.setUserEmail(userEntity.getUserEmail());
                updatedUser.setUserPassword(userEntity.getUserPassword());
                updatedUser.setUserRole(userEntity.getUserRole().toString());
                updatedUser.setTeamId(teamEntity.getTeamId());

                userRepository.save(userEntity);

                Set<RoleEntity> authorities = new HashSet<>();
                authorities.add(roleRepository.findByAuthority("USER").get());

                auth.setUserId(userEntity.getUserId());
                auth.setUserPassword(userEntity.getUserPassword());
                auth.setAuthorities(authorities);
                authRepository.save(auth);
                return updatedUser;
            }
        }
    }
}
