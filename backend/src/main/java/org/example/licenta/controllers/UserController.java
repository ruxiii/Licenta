package org.example.licenta.controllers;

//import org.example.licenta.configuration.UserAuthenticationProvider;
import org.example.licenta.dto.UserDto;
import org.example.licenta.dto.UserFullDto;
import org.example.licenta.exceptions.TeamNotFoundException;
import org.example.licenta.exceptions.UserNotFoundException;
import org.example.licenta.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

//    private final UserAuthenticationProvider userAuthenticationProvider;
//
//    public UserController(UserAuthenticationProvider userAuthenticationProvider) {
//        this.userAuthenticationProvider = userAuthenticationProvider;
//    }


    //    TODO: ASTA NU TREBUIE SA FIE LA USER
    @GetMapping("/users")
    public List<UserFullDto> getUsers() throws UserNotFoundException {
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public UserFullDto getUserById(@PathVariable String id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @DeleteMapping("/users/{id}/delete")
    public void deleteUser(@PathVariable String id) throws UserNotFoundException {
        userService.deleteUser(id);
    }

    @PostMapping("/users/create")
    public ResponseEntity<UserFullDto> createUser(@RequestBody UserDto userDto) throws TeamNotFoundException {
        UserFullDto userFullDto = userService.createUser(userDto);
//        userFullDto.setToken(userAuthenticationProvider.createToken(userFullDto));
        return ResponseEntity.ok(userFullDto);
    }
    
    @PutMapping("/users/{id}/update")
    public UserFullDto updateUser(@RequestBody UserFullDto userFullDto, @PathVariable String id) throws UserNotFoundException, TeamNotFoundException {
        return userService.updateUser(userFullDto, id);
    }
}
