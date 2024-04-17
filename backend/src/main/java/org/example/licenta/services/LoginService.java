package org.example.licenta.services;

import org.example.licenta.db.repositories.AuthenticationRepository;
import org.example.licenta.dto.LoginResponseDto;
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

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationRepository authRepository;

    public LoginResponseDto loginUser(String username, String password){
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);

                String token = tokenService.generateJwt(auth);

                return new LoginResponseDto(authRepository.findById(username).get(), token);
            }else {
                return new LoginResponseDto(null, "");
            }

        } catch(AuthenticationException e){
            return new LoginResponseDto(null, "");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        return authRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
