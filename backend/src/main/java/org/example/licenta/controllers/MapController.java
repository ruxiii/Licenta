package org.example.licenta.controllers;

import org.example.licenta.services.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MapController {

    @Autowired
    private MapService mapService;

//    @PostMapping("/creazaHarta")
//    public String uploadHarta(@RequestParam("file") MultipartFile file, @RequestParam("denumireHarta") String denumireHarta, @RequestParam("idDepartament") Long idDepartament) {
}
