package org.example.infsyslab1.controller;

import org.example.infsyslab1.dto.CoordinatesDTO;
import org.example.infsyslab1.service.CoordinatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coordinates")
public class CoordinatesController {
    private final CoordinatesService coordinatesService;

    @Autowired
    public CoordinatesController(CoordinatesService coordinatesService){
        this.coordinatesService = coordinatesService;
    }

    @PostMapping
    public ResponseEntity<CoordinatesDTO> createCoordinates(@RequestBody CoordinatesDTO coordinatesDTO){
        return new ResponseEntity<>(coordinatesService.createCoordinates(coordinatesDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoordinatesDTO> updateCoordinates(@PathVariable Long id, @RequestBody CoordinatesDTO coordinatesDTO){
        return new ResponseEntity<>(coordinatesService.updateCoordinates(id, coordinatesDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoordinates(@PathVariable Long id){
        coordinatesService.deleteCoordinates(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoordinatesDTO> getCoordinatesById(@PathVariable Long id){
        return new ResponseEntity<>(coordinatesService.getCoordinatesById(id), HttpStatus.OK);
    }
}
