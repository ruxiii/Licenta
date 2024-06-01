package org.example.licenta.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "Welcome to the home page!";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Welcome to the admin page!";
    }

    @GetMapping("/login")
    public String login() {
        return "Welcome to the login page!";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the welcome page!";
    }
}
