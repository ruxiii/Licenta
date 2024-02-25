package org.example.licenta.controllers;

import org.example.licenta.dto.MapDto;
import org.example.licenta.exceptions.DepartmentNotFoundException;
import org.example.licenta.exceptions.MapAlreadyExistsException;
import org.example.licenta.exceptions.MapNotFoundException;
import org.example.licenta.services.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class MapController {

    @Autowired
    private MapService mapService;

//    TODO: implement this method
//    @PostMapping("/uploadMap")
//    public String uploadMap(@RequestParam("file") MultipartFile file, @RequestParam("mapName") String mapName,
//                            @RequestParam("departmentId") Long departmentId) {
//        mapService.saveMap(file, mapName, departmentId);
//        return "Map uploaded successfully";
//    }

    @GetMapping("/maps")
    public List<MapDto> getMaps() throws MapNotFoundException {
        return mapService.getMaps();
    }

    @GetMapping("/maps/{id}")
    public MapDto getMapById(@PathVariable String id) throws MapNotFoundException {
        return mapService.getMapById(id);
    }

//    TODO: implement this method
//    @GetMapping("/getMapImage")
//    public String getMapImage() {
//        return "Map Image";
//    }

    @DeleteMapping("/maps/{id}/delete")
    public void deleteMap(@PathVariable String id) throws MapNotFoundException {
        mapService.deleteMap(id);
    }

    @PostMapping("/maps/create")
    public void createMap(@RequestBody MapDto mapDto) throws MapAlreadyExistsException, DepartmentNotFoundException {
        mapService.createMap(mapDto);
    }

    @PutMapping("/maps/{id}/update")
    public MapDto updateMap(@RequestBody MapDto mapDto, @PathVariable String id) throws MapNotFoundException, DepartmentNotFoundException {
        return mapService.updateMap(mapDto, id);
    }


}
