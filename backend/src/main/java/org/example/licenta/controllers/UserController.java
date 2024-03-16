package org.example.licenta.controllers;

import org.example.licenta.dto.UserDto;
import org.example.licenta.dto.UserFullDto;
import org.example.licenta.exceptions.TeamNotFoundException;
import org.example.licenta.exceptions.UserAlreadyExistsException;
import org.example.licenta.exceptions.UserNotFoundException;
import org.example.licenta.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserFullDto> getUsers() throws UserNotFoundException {
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public UserDto getUserById(@PathVariable String id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @DeleteMapping("/users/{id}/delete")
    public void deleteUser(@PathVariable String id) throws UserNotFoundException {
        userService.deleteUser(id);
    }

    @PostMapping("/users/create")
    public void createUser(@RequestBody UserDto userDto) throws UserAlreadyExistsException, TeamNotFoundException, NoSuchAlgorithmException {
        userService.createUser(userDto);
    }
    
    @PutMapping("/users/{id}/update")
    public UserDto updateUser(@RequestBody UserDto userDto, @PathVariable String id) throws UserNotFoundException, TeamNotFoundException {
        return userService.updateUser(userDto, id);
    }
}
