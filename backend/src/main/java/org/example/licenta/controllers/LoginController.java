package org.example.licenta.controllers;

import org.example.licenta.dto.AuthenticationDto;
import org.example.licenta.dto.LoginResponseDto;
import org.example.licenta.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody AuthenticationDto authenticationDto){
        return loginService.loginUser(authenticationDto.getUserId(), authenticationDto.getUserPassword());
    }
}
