package org.example.licenta.services;

import org.example.licenta.db.repositories.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
