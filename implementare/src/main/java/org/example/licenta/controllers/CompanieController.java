package org.example.licenta.controllers;

import org.example.licenta.repositories.CompanieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanieController {

    @Autowired
    private CompanieRepository companieRepository;
}
