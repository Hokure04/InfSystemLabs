package org.example.infsyslab1.dto;

import lombok.Data;
import org.example.infsyslab1.model.Coordinates;
import org.example.infsyslab1.model.Location;
import org.example.infsyslab1.model.User;
import org.example.infsyslab1.model.enums.Color;
import org.example.infsyslab1.model.enums.Country;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PersonDTO {
    private Long id;
    private String name;
    private LocalDateTime creationDate;
    private LocalDate birthday;
    private Color eyeColor;
    private Color hairColor;
    private Location location;
    private Coordinates coordinates;
    private User user;
    private Integer height;
    private Country nationality;
}
