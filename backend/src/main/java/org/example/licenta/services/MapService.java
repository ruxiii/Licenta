package org.example.licenta.services;

import org.example.licenta.dto.MapDto;
import org.example.licenta.repositories.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Base64;

@Service
public class MapService {

    @Autowired
    private MapRepository mapRepository;

    public void saveMap(MultipartFile file, String denumireHarta, Long idDepartament) {
        MapDto mapDto = new MapDto();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
//            TODO: throw new Exception("not a valid file");
            System.out.println("not a valid file");
        }
        mapDto.setDenumireHarta(denumireHarta);
        try {
            mapDto.setPozaHarta(Base64.getEncoder().encodeToString(file.getBytes()));
        }
        catch (IOException e) {
//            TODO: throw new Exception("not a valid file");
            e.printStackTrace();
        }

        mapDto.setIdDepartament(idDepartament);
//        hartaRepository.save(hartaDto);
    }
}
