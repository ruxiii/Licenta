package org.example.licenta.services;

import org.example.licenta.db.entities.MapEntity;
import org.example.licenta.db.entities.PlaceEntity;
import org.example.licenta.db.repositories.MapRepository;
import org.example.licenta.db.repositories.PlaceRepository;
import org.example.licenta.dto.PlaceDto;
import org.example.licenta.exceptions.MapNotFoundException;
import org.example.licenta.exceptions.PlaceAlreadyExistsException;
import org.example.licenta.exceptions.PlaceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private MapRepository mapRepository;

    public List<PlaceDto> getPlaces() throws PlaceNotFoundException {
        List<PlaceEntity> places = placeRepository.findAll();
        if(places.isEmpty()) {
            throw new PlaceNotFoundException("No places found");
        }
        else{
            List<PlaceDto> placeDtos = new ArrayList<>();
            for(PlaceEntity place : places) {
                PlaceDto placeDto = new PlaceDto();
                placeDto.setPlaceNameId(place.getPlaceNameId());
                placeDto.setMapNameId(place.getMapEntity().getMapNameId());
                placeDtos.add(placeDto);
            }
            return placeDtos;
        }
    }

    public PlaceDto getPlaceById(String id) throws PlaceNotFoundException {
        PlaceEntity place = placeRepository.findById(id).orElse(null);
        if(place == null) {
            throw new PlaceNotFoundException("Place not found");
        }
        else {
            PlaceDto placeDto = new PlaceDto();
            placeDto.setPlaceNameId(place.getPlaceNameId());
            placeDto.setMapNameId(place.getMapEntity().getMapNameId());
            return placeDto;
        }
    }

    public void deletePlace(String id) throws PlaceNotFoundException {
        PlaceEntity place = placeRepository.findById(id).orElse(null);
        if(place == null) {
            throw new PlaceNotFoundException("Place not found");
        }
        else {
            placeRepository.delete(place);
        }
    }

    public void createPlace(PlaceDto placeDto) throws MapNotFoundException, PlaceAlreadyExistsException {
        if (mapRepository.findById(placeDto.getMapNameId()).orElse(null) == null) {
            throw new MapNotFoundException("Map not found");
        }
        else {
            if (placeRepository.findById(placeDto.getPlaceNameId()).isPresent()) {
                throw new PlaceAlreadyExistsException("Place already exists");
            }
            else {
                PlaceEntity place = new PlaceEntity();
                place.setPlaceNameId(placeDto.getPlaceNameId());
                place.setMapEntity(mapRepository.findById(placeDto.getMapNameId()).orElse(null));
                placeRepository.save(place);
            }
        }
    }

    public PlaceDto updatePlace(PlaceDto placeDto, String id) throws PlaceNotFoundException {
        PlaceEntity place = placeRepository.findById(id).orElse(null);
        if(place == null) {
            throw new PlaceNotFoundException("Place not found");
        }
        else {
            MapEntity mapEntity = mapRepository.findById(placeDto.getMapNameId()).orElse(null);
            if(mapEntity == null) {
                throw new PlaceNotFoundException("Map not found");
            }
            else {
                place.setPlaceNameId(placeDto.getPlaceNameId());
                place.setMapEntity(mapEntity);
                placeRepository.save(place);

                PlaceDto placeDto1 = new PlaceDto();
                placeDto1.setPlaceNameId(place.getPlaceNameId());
                placeDto1.setMapNameId(place.getMapEntity().getMapNameId());
                return placeDto1;
            }
        }
    }
}
