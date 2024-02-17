package org.example.licenta.controllers;

import org.example.licenta.db.entities.UserEntity;
import org.example.licenta.dto.UserDto;
import org.example.licenta.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public UserDto getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/users/{id}/delete")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @PostMapping("/users/create")
    public void createUser(UserDto userDto) {
        userService.createUser(userDto);
    }
    
    @PutMapping("/users/{id}/update")
    public UserDto updateUser(@RequestBody UserDto userDto, @PathVariable String id) {
        return userService.updateUser(userDto, id);
    }
}
