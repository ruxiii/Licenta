package org.example.licenta.controllers;

import org.example.licenta.db.entities.MapEntity;
import org.example.licenta.dto.MapDto;
import org.example.licenta.exceptions.MapAlreadyExistsException;
import org.example.licenta.exceptions.MapNotFoundException;
import org.example.licenta.services.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

    @PostMapping(value = "/maps/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public MapEntity createMap(@RequestPart("map") MapDto mapDto,
                          @RequestPart("mapFile") MultipartFile file) throws MapAlreadyExistsException {
        try {
            MapEntity mapEntity = updateMapImage(file);
            mapDto.setMapImage(mapEntity.getMapImage());
            return mapService.createMap(mapDto);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping("/maps/{id}/update")
    public MapDto updateMap(@RequestBody MapDto mapDto, @PathVariable String id) throws MapNotFoundException {
        return mapService.updateMap(mapDto, id);
    }

    public MapEntity updateMapImage(MultipartFile multipartFile) throws IOException {
        return new MapEntity(multipartFile.getOriginalFilename(), multipartFile.getContentType(), multipartFile.getBytes());
    }


}
