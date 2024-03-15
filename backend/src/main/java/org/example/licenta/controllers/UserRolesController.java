package org.example.licenta.controllers;

import org.example.licenta.db.entities.enums.UserRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserRolesController {

    @GetMapping("/userRoles")
    public ResponseEntity<List<String>> getUserRoles() {
        List<String> roles = Arrays.stream(UserRoles.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }
}
