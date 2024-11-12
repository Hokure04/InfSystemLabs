package org.example.infsyslab1.service;

import org.example.infsyslab1.dto.LocationDTO;
import org.example.infsyslab1.model.Location;
import org.example.infsyslab1.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository){
        this.locationRepository = locationRepository;
    }

    public LocationDTO createLocation(LocationDTO locationDTO){
        Location location = new Location();
        location.setX(locationDTO.getX());
        location.setY(locationDTO.getY());
        location.setZ(locationDTO.getZ());
        Location saved = locationRepository.save(location);
        return mapToDTO(saved);
    }

    public LocationDTO updateLocation(Long id, LocationDTO locationDTO){
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        location.setX(locationDTO.getX());
        location.setY(locationDTO.getY());
        location.setZ(locationDTO.getZ());
        Location updated = locationRepository.save(location);
        return mapToDTO(updated);
    }

    public void deleteCoordinates(Long id){
        locationRepository.deleteById(id);
    }

    public LocationDTO getLocationById(Long id){
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        return mapToDTO(location);
    }

    private LocationDTO mapToDTO(Location location){
        LocationDTO dto = new LocationDTO();
        dto.setId(location.getId());
        dto.setX(location.getX());
        dto.setY(location.getY());
        dto.setZ(location.getZ());
        return dto;
    }
}
