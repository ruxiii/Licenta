package org.example.licenta.controllers;

import org.example.licenta.services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceController {

    @Autowired
    private PlaceService placeService;
}
