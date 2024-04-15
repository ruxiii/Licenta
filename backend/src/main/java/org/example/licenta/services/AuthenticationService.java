package org.example.licenta.services;

import jakarta.transaction.Transactional;
import org.example.licenta.db.entities.AuthenticationEntity;
import org.example.licenta.db.repositories.AuthenticationRepository;
import org.example.licenta.db.repositories.RoleRepository;
import org.example.licenta.db.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserService userService;

//    public AuthenticationEntity registerUser(AuthenticationEntity authenticationEntity) {
//        authenticationEntity.setPassword(passwordEncoder.encode(authenticationEntity.getPassword()));
//        return authenticationRepository.save(authenticationEntity);
//    }
}
