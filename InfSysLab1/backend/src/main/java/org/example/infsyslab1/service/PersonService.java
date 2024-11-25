package org.example.infsyslab1.service;

import org.example.infsyslab1.dto.CoordinatesDTO;
import org.example.infsyslab1.dto.LocationDTO;
import org.example.infsyslab1.dto.PersonDTO;
import org.example.infsyslab1.dto.UserDTO;
import org.example.infsyslab1.model.Coordinates;
import org.example.infsyslab1.model.Location;
import org.example.infsyslab1.model.Person;
import org.example.infsyslab1.model.User;
import org.example.infsyslab1.repository.PersonRepository;
import org.example.infsyslab1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final UserRepository userRepository;


    @Autowired
    public PersonService(PersonRepository personRepository, UserRepository userRepository){
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    public PersonDTO createPerson(PersonDTO personDTO) {
        Person person = mapToEntity(personDTO);
        if (personDTO.getUser() != null && personDTO.getUser().getId() != null) {
            User user = userRepository.findById(personDTO.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            person.setUser(user);
        }
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
        person.setNationality(personDTO.getNationality());

        if (personDTO.getCoordinates() != null) {
            Coordinates coordinates = new Coordinates();
            coordinates.setX(personDTO.getCoordinates().getX());
            coordinates.setY(personDTO.getCoordinates().getY());
            person.setCoordinates(coordinates);
        }

        if (personDTO.getLocation() != null) {
            Location location = new Location();
            location.setX(personDTO.getLocation().getX());
            location.setY(personDTO.getLocation().getY());
            location.setZ(personDTO.getLocation().getZ());
            person.setLocation(location);
        }

        if (personDTO.getUser() != null) {
            User user = new User();
            user.setUsername(personDTO.getUser().getUsername());
            user.setRole(personDTO.getUser().getRole());
            person.setUser(user);
        }
        return mapToDTO(personRepository.save(person));
    }

    public void deletePerson(Long id){
        personRepository.deleteById(id);
    }

    public List<PersonDTO> getAllPersons(String filter, String sortBy){
        List<Person> persons = personRepository.findAll();
        if(filter != null && !filter.isEmpty()){
            persons = persons.stream()
                    .filter(person -> person.getName().equalsIgnoreCase(filter))
                    .collect(Collectors.toList());
        }
        if(sortBy != null && !sortBy.isEmpty()){
            persons.sort(Comparator.comparing(person -> switch (sortBy.toLowerCase()) {
                case "name" -> person.getName();
                case "height" -> person.getHeight().toString();
                default -> person.getName();
            }));
        }
        return persons.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO getPersonById(Long id){
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        return mapToDTO(person);
    }

    public int calculateHeightSum(){
        return personRepository.findAll().stream()
                .mapToInt(Person::getHeight)
                .sum();
    }

    public List<PersonDTO> getPersonByNamePrefix(String prefix){
        return personRepository.findAll().stream()
                .filter(person -> person.getName().startsWith(prefix))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<Integer> getUniqueHeights(){
        return personRepository.findAll().stream()
                .map(Person::getHeight)
                .distinct()
                .collect(Collectors.toList());
    }

    public double getHairPercentage(String hairColor){
        long total = personRepository.count();
        if(total == 0) return 0;
        long matching = personRepository.findAll().stream()
                .filter(person -> person.getHairColor() != null && person.getHairColor().name().equalsIgnoreCase(hairColor))
                .count();

        return (double) matching/total *100;
    }

    private Person mapToEntity(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());
        person.setEyeColor(personDTO.getEyeColor());
        person.setHairColor(personDTO.getHairColor());
        person.setHeight(personDTO.getHeight());
        person.setBirthday(personDTO.getBirthday());
        person.setNationality(personDTO.getNationality());

        if (personDTO.getCoordinates() != null) {
            Coordinates coordinates = new Coordinates();
            coordinates.setX(personDTO.getCoordinates().getX());
            coordinates.setY(personDTO.getCoordinates().getY());
            person.setCoordinates(coordinates);
        }

        if (personDTO.getLocation() != null) {
            Location location = new Location();
            location.setX(personDTO.getLocation().getX());
            location.setY(personDTO.getLocation().getY());
            location.setZ(personDTO.getLocation().getZ());
            person.setLocation(location);
        }

        if (personDTO.getUser() != null) {
            User user = new User();
            user.setUsername(personDTO.getUser().getUsername());
            user.setRole(personDTO.getUser().getRole());
            person.setUser(user);
        }

        return person;
    }

    private PersonDTO mapToDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setName(person.getName());
        personDTO.setBirthday(person.getBirthday());
        personDTO.setHeight(person.getHeight());
        personDTO.setCreationDate(person.getCreationDate());
        personDTO.setEyeColor(person.getEyeColor());
        personDTO.setHairColor(person.getHairColor());
        personDTO.setNationality(person.getNationality());

        if (person.getCoordinates() != null) {
            CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
            coordinatesDTO.setX(person.getCoordinates().getX());
            coordinatesDTO.setY(person.getCoordinates().getY());
            personDTO.setCoordinates(coordinatesDTO);
        }

        if (person.getLocation() != null) {
            LocationDTO locationDTO = new LocationDTO();
            locationDTO.setX(person.getLocation().getX());
            locationDTO.setY(person.getLocation().getY());
            locationDTO.setZ(person.getLocation().getZ());
            personDTO.setLocation(locationDTO);
        }

        if (person.getUser() != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(person.getUser().getId()); // Если User имеет поле id
            userDTO.setUsername(person.getUser().getUsername());
            userDTO.setRole(person.getUser().getRole());
            personDTO.setUser(userDTO);
        }

        return personDTO;
    }

}
