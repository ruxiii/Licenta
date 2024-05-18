package org.example.licenta.controllers;

import org.example.licenta.db.entities.MapEntity;
import org.example.licenta.dto.MapDto;
import org.example.licenta.exceptions.MapAlreadyExistsException;
import org.example.licenta.exceptions.MapNotFoundException;
import org.example.licenta.services.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MapController {

    @Autowired
    private MapService mapService;

    @GetMapping("/maps")
    public List<MapDto> getMaps() throws MapNotFoundException {
        return mapService.getMaps();
    }

    @GetMapping("/maps/{id}/availabilities/{date}")
    public Map<MapEntity, Object> getMapById(@PathVariable String id, @PathVariable String date) throws MapNotFoundException {
        return mapService.getMapById(id, date);
    }

    @DeleteMapping("/maps/{id}/delete")
    public void deleteMap(@PathVariable String id) throws MapNotFoundException {
        mapService.deleteMap(id);
    }

    @PostMapping(value = "/maps/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void createMap(String id,
                          @RequestPart("file") MultipartFile file) throws MapAlreadyExistsException {
        try {
            mapService.createMap(id, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/maps/{id}/update")
    public MapDto updateMap(@RequestBody MapDto mapDto, @PathVariable String id) throws MapNotFoundException {
        return mapService.updateMap(mapDto, id);
    }
}
