package org.example.infsyslab1.controller;

import org.example.infsyslab1.dto.PersonDTO;
import org.example.infsyslab1.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO){
        return new ResponseEntity<>(personService.createPerson(personDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long id, @RequestBody PersonDTO personDTO){
        return new ResponseEntity<>(personService.updatePerson(id, personDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id){
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) String sortBy
    ){
        return new ResponseEntity<>(personService.getAllPersons(filter, sortBy), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id){
        return new ResponseEntity<>(personService.getPersonById(id), HttpStatus.OK);
    }

    @GetMapping("/sum-height")
    public ResponseEntity<Integer> calculateHeightSum(){
        return new ResponseEntity<>(personService.calculateHeightSum(), HttpStatus.OK);
    }

    @GetMapping("/by-name-prefix")
    public ResponseEntity<List<PersonDTO>> getPersonsByNamePrefix(@RequestParam String prefix){
        return new ResponseEntity<>(personService.getPersonByNamePrefix(prefix), HttpStatus.OK);
    }
    @GetMapping("unique-heights")
    public ResponseEntity<List<Integer>> getUniqueHeights(){
        return new ResponseEntity<>(personService.getUniqueHeights(), HttpStatus.OK);
    }

    @GetMapping("/hair-color-percentage")
    public  ResponseEntity<Double> getHairColorPercentage(@RequestParam String hairColor){
        return new ResponseEntity<>(personService.getHairPercentage(hairColor), HttpStatus.OK);
    }
}
