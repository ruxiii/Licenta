package org.example.licenta.services;

import org.example.licenta.db.entities.DepartmentEntity;
import org.example.licenta.db.entities.MapEntity;
import org.example.licenta.db.repositories.DepartmentRepository;
import org.example.licenta.db.repositories.MapRepository;
import org.example.licenta.dto.MapDto;
import org.example.licenta.exceptions.DepartmentNotFoundException;
import org.example.licenta.exceptions.MapNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MapService {

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

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
                mapDto.setMapName(map.getMapName());
                mapDto.setMapImage(map.getMapImage());
                mapDto.setDepartmentId(map.getDepartmentEntity().getDepartmentId());
                mapDtos.add(mapDto);
            }
            return mapDtos;
        }
    }

    public MapDto getMapById(String id) throws MapNotFoundException {
        Optional<MapEntity> map = mapRepository.findById(Long.valueOf(id));
        if (map.isEmpty()) {
            throw new MapNotFoundException("Map not found");
        }
        else {
            MapDto mapDto = new MapDto();
            mapDto.setMapName(map.get().getMapName());
            mapDto.setMapImage(map.get().getMapImage());
            mapDto.setDepartmentId(map.get().getDepartmentEntity().getDepartmentId());
            return mapDto;
        }
    }

    public void deleteMap(String id) throws MapNotFoundException {
        if (mapRepository.existsById(Long.valueOf(id))) {
            mapRepository.deleteById(Long.valueOf(id));
        }
        else {
            throw new MapNotFoundException("Map not found");
        }
    }

    public void createMap(MapDto mapDto) throws DepartmentNotFoundException {
        if (!departmentRepository.existsById(mapDto.getDepartmentId())) {
            throw new DepartmentNotFoundException("Department not found");
        }
        else{
            DepartmentEntity departmentEntity = departmentRepository.findById(mapDto.getDepartmentId()).get();
            MapEntity mapEntity = new MapEntity();
            mapEntity.setMapName(mapDto.getMapName());
            mapEntity.setMapImage(mapDto.getMapImage());
            mapEntity.setDepartmentEntity(departmentEntity);
            mapRepository.save(mapEntity);
        }
    }

    public MapDto updateMap(MapDto mapDto, String id) throws MapNotFoundException, DepartmentNotFoundException {
        if (!mapRepository.existsById(Long.valueOf(id))) {
            throw new MapNotFoundException("Map not found");
        }
        else {
            if (!departmentRepository.existsById(mapDto.getDepartmentId())) {
                throw new DepartmentNotFoundException("Department not found");
            }
            else {
                MapEntity mapEntity = mapRepository.findById(Long.valueOf(id)).get();
                mapEntity.setMapName(mapDto.getMapName());
                mapEntity.setMapImage(mapDto.getMapImage());
                DepartmentEntity departmentEntity = departmentRepository.findById(mapDto.getDepartmentId()).get();
                mapEntity.setDepartmentEntity(departmentEntity);
                mapRepository.save(mapEntity);

                MapDto mapDto1 = new MapDto();
                mapDto1.setMapName(mapEntity.getMapName());
                mapDto1.setMapImage(mapEntity.getMapImage());
                mapDto1.setDepartmentId(mapEntity.getDepartmentEntity().getDepartmentId());
                return mapDto1;
            }
        }
    }
}
