package org.example.infsyslab1.service;

import org.example.infsyslab1.dto.PersonDTO;
import org.example.infsyslab1.dto.UserDTO;
import org.example.infsyslab1.model.Coordinates;
import org.example.infsyslab1.model.Location;
import org.example.infsyslab1.model.Person;
import org.example.infsyslab1.model.User;
import org.example.infsyslab1.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    public PersonDTO createPerson(PersonDTO personDTO){
        Person person = mapToEntity(personDTO);
        person = personRepository.save(person);
        return mapToDTO(person);
    }

    public PersonDTO updatePerson(Long id, PersonDTO personDTO){
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("person not found"));
        person.setName(personDTO.getName());
        person.setEyeColor(personDTO.getEyeColor());
        person.setHairColor(personDTO.getHairColor());
        person.setBirthday(personDTO.getBirthday());
        person.setHeight(personDTO.getHeight());
        person.setCreationDate(personDTO.getCreationDate());
        person.setLocation(personDTO.getLocation());
        person.setCoordinates(personDTO.getCoordinates());
        person.setUser(personDTO.getUser());
        person.setNationality(personDTO.getNationality());
        return mapToDTO(personRepository.save(person));
    }

    public void deletePerson(Long id){
        personRepository.deleteById(id);
    }

    public List<PersonDTO> getAllPersons(){
        return personRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO getPersonById(Long id){
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        return mapToDTO(person);
    }



    private Person mapToEntity(PersonDTO personDTO){
        Person person = new Person();
        person.setName(personDTO.getName());
        person.setEyeColor(personDTO.getEyeColor());
        person.setHairColor(personDTO.getHairColor());
        person.setHeight(person.getHeight());
        person.setBirthday(personDTO.getBirthday());
        person.setNationality(personDTO.getNationality());

        if (personDTO.getCoordinates() != null){
            Coordinates coordinates = new Coordinates();
            coordinates.setX(personDTO.getCoordinates().getX());
            coordinates.setY(personDTO.getCoordinates().getY());
            person.setCoordinates(coordinates);
        }

        if(personDTO.getLocation() != null){
            Location location = new Location();
            location.setX(personDTO.getLocation().getX());
            location.setY(personDTO.getLocation().getY());
            location.setZ(personDTO.getLocation().getZ());
            person.setLocation(location);
        }

        return person;

    }

    private UserDTO mapToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userDTO.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    private PersonDTO mapToDTO(Person person){
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(personDTO.getId());
        personDTO.setName(personDTO.getName());
        personDTO.setBirthday(personDTO.getBirthday());
        personDTO.setCoordinates(personDTO.getCoordinates());
        personDTO.setHeight(personDTO.getHeight());
        personDTO.setCreationDate(personDTO.getCreationDate());
        personDTO.setEyeColor(personDTO.getEyeColor());
        personDTO.setHairColor(personDTO.getHairColor());
        personDTO.setUser(personDTO.getUser());
        return personDTO;
    }
}
