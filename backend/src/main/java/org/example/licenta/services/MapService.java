package org.example.licenta.services;

import org.example.licenta.db.entities.MapEntity;
import org.example.licenta.db.repositories.MapRepository;
import org.example.licenta.dto.MapDto;
import org.example.licenta.exceptions.DepartmentNotFoundException;
import org.example.licenta.exceptions.MapAlreadyExistsException;
import org.example.licenta.exceptions.MapNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MapService {

    @Autowired
    private MapRepository mapRepository;

    // TODO: implement this method
//    public void saveMap(MultipartFile file, String denumireHarta, Long idDepartament) {
//        MapDto mapDto = new MapDto();
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        if (fileName.contains("..")) {
////            TODO: throw new Exception("not a valid file");
//            System.out.println("not a valid file");
//        }
//        mapDto.setDenumireHarta(denumireHarta);
//        try {
//            mapDto.setPozaHarta(Base64.getEncoder().encodeToString(file.getBytes()));
//        }
//        catch (IOException e) {
////            TODO: throw new Exception("not a valid file");
//            e.printStackTrace();
//        }
//
//        mapDto.setIdDepartament(idDepartament);
////        mapRepository.save(mapDto);
//    }

    public List<MapDto> getMaps() throws MapNotFoundException {
        if (mapRepository.findAll().isEmpty()) {
            throw new MapNotFoundException("No maps found");
        }
        else {
            List<MapEntity> maps = mapRepository.findAll();
            List<MapDto> mapDtos = new ArrayList<>();
            for (MapEntity map : maps) {
                MapDto mapDto = new MapDto();
                mapDto.setMapNameId(map.getMapNameId());
//                mapDto.setMapImage(map.getMapImage());
                mapDtos.add(mapDto);
            }
            return mapDtos;
        }
    }

    public MapDto getMapById(String id) throws MapNotFoundException {
        Optional<MapEntity> map = mapRepository.findById(id);
        if (map.isEmpty()) {
            throw new MapNotFoundException("Map not found");
        }
        else {
            MapDto mapDto = new MapDto();
            mapDto.setMapNameId(map.get().getMapNameId());
//            mapDto.setMapImage(map.get().getMapImage());
            return mapDto;
        }
    }

    public void deleteMap(String id) throws MapNotFoundException {
        if (mapRepository.existsById(id)) {
            mapRepository.deleteById(id);
        }
        else {
            throw new MapNotFoundException("Map not found");
        }
    }

    public MapEntity createMap(MapDto mapDto, MultipartFile file) throws MapAlreadyExistsException, IOException {
        if (mapRepository.existsById(mapDto.getMapNameId())) {
                throw new MapAlreadyExistsException("Map already exists");
        }
        else {
            MapEntity mapEntity = new MapEntity();
            mapEntity.setMapNameId(mapDto.getMapNameId());
            mapEntity.setMapName(file.getOriginalFilename());
            mapEntity.setMapType(file.getContentType());
            mapEntity.setMapImage(file.getBytes());

            mapRepository.save(mapEntity);
            return mapEntity;
        }
    }

//    public MapEntity updateMapImage(MultipartFile multipartFile) throws IOException {
//        return new MapEntity(multipartFile.getOriginalFilename(), multipartFile.getContentType(), multipartFile.getBytes());
//    }

    public MapDto updateMap(MapDto mapDto, String id) throws MapNotFoundException {
//        if (!mapRepository.existsById(id)) {
//            throw new MapNotFoundException("Map not found");
//        }
//        else {
//            MapEntity mapEntity = mapRepository.findById(id).get();
//            mapEntity.setMapNameId(mapDto.getMapNameId());
//            mapEntity.setMapImage(mapDto.getMapImage());
//            mapRepository.save(mapEntity);
//
//            MapDto mapDto1 = new MapDto();
//            mapDto1.setMapNameId(mapEntity.getMapNameId());
//            mapDto1.setMapImage(mapEntity.getMapImage());
//            return mapDto1;
//        }
        return null;
    }
}
