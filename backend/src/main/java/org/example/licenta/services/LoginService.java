package org.example.licenta.services;

import org.example.licenta.db.entities.UserEntity;
import org.example.licenta.db.repositories.AuthenticationRepository;
import org.example.licenta.db.repositories.UserRepository;
import org.example.licenta.dto.LoginResponseDto;
import org.example.licenta.dto.UserFullDto;
import org.example.licenta.exceptions.AuthenticationFailed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationRepository authRepository;

    @Autowired
    private UserRepository userRepository;

    public UserFullDto loginUser(String username, String password) throws AuthenticationFailed {
        UserEntity userEntity = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (passwordEncoder.matches(password, userEntity.getUserPassword())) {
            UserFullDto userFullDto = new UserFullDto();
            userFullDto.setUserId(userEntity.getUserId());
            userFullDto.setUserName(userEntity.getUserName());
            userFullDto.setUserFirstName(userEntity.getUserFirstName());
            userFullDto.setUserEmail(userEntity.getUserEmail());
            userFullDto.setUserPassword(userEntity.getUserPassword());
            userFullDto.setUserRole(userEntity.getUserRole());
            userFullDto.setTeamId(userEntity.getTeamEntity().getTeamId());
            return userFullDto;
            }

        throw new AuthenticationFailed("Invalid password");
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        return authRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
