package org.example.infsyslab1.service;

import org.example.infsyslab1.dto.CoordinatesDTO;
import org.example.infsyslab1.model.Coordinates;
import org.example.infsyslab1.repository.CoordinatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoordinatesService {
    private final CoordinatesRepository coordinatesRepository;

    @Autowired
    public CoordinatesService(CoordinatesRepository coordinatesRepository){
        this.coordinatesRepository = coordinatesRepository;
    }

    public CoordinatesDTO createCoordinates(CoordinatesDTO coordinatesDTO){
        Coordinates coordinates = new Coordinates();
        coordinates.setX(coordinatesDTO.getX());
        coordinates.setY(coordinatesDTO.getY());
        Coordinates saved = coordinatesRepository.save(coordinates);
        return mapToDTO(saved);
    }

    public CoordinatesDTO updateCoordinates(Long id, CoordinatesDTO coordinatesDTO){
        Coordinates coordinates = coordinatesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordinates not found"));
        coordinates.setX(coordinatesDTO.getX());
        coordinates.setY(coordinatesDTO.getY());
        Coordinates updated = coordinatesRepository.save(coordinates);
        return mapToDTO(updated);
    }

    public void deleteCoordinates(Long id){
        coordinatesRepository.deleteById(id);
    }

    public CoordinatesDTO getCoordinatesById(Long id){
        Coordinates coordinates = coordinatesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordinates not found"));
        return mapToDTO(coordinates);
    }

    private CoordinatesDTO mapToDTO(Coordinates coordinates){
        CoordinatesDTO dto = new CoordinatesDTO();
        dto.setId(coordinates.getId());
        dto.setX(coordinates.getX());
        dto.setY(coordinates.getY());
        return dto;
    }
}
