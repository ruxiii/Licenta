package org.example.licenta.authentication;

import org.example.licenta.db.entities.AuthenticationEntity;
import org.example.licenta.db.entities.UserEntity;
import org.example.licenta.db.repositories.AuthenticationRepository;
import org.example.licenta.db.repositories.UserRepository;
import org.example.licenta.authentication.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthenticationEntity user = authenticationRepository.findByUserId(username);
        if (user == null) {
           throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }
}
