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

    @DeleteMapping
    public ResponseEntity<Void> deletePerson(@PathVariable Long id){
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons(){
        return new ResponseEntity<>(personService.getAllPersons(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id){
        return new ResponseEntity<>(personService.getPersonById(id), HttpStatus.OK);
    }
}
