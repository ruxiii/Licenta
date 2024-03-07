package org.example.licenta.controllers;

import org.example.licenta.dto.PlaceDto;
import org.example.licenta.exceptions.MapNotFoundException;
import org.example.licenta.exceptions.PlaceAlreadyExistsException;
import org.example.licenta.exceptions.PlaceNotFoundException;
import org.example.licenta.services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping("/places")
    public List<PlaceDto> getPlaces() throws PlaceNotFoundException {
        return placeService.getPlaces();
    }

    @GetMapping("/places/{id}")
    public PlaceDto getPlaceById(@PathVariable String id) throws PlaceNotFoundException {
        return placeService.getPlaceById(id);
    }

    @DeleteMapping("/places/{id}/delete")
    public void deletePlace(@PathVariable String id) throws PlaceNotFoundException {
        placeService.deletePlace(id);
    }

    @PostMapping("/places/create")
    public void createPlace(@RequestBody PlaceDto placeDto) throws PlaceAlreadyExistsException, MapNotFoundException {
        placeService.createPlace(placeDto);
    }

    @PutMapping("/places/{id}/update")
    public PlaceDto updatePlace(@RequestBody PlaceDto placeDto, @PathVariable String id) throws PlaceNotFoundException {
        return placeService.updatePlace(placeDto, id);
    }
}
