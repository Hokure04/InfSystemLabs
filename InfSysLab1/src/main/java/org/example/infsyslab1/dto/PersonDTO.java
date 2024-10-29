package org.example.infsyslab1.dto;

import org.example.infsyslab1.model.enums.Color;
import org.example.infsyslab1.model.enums.Country;
import java.time.LocalDateTime;

public class PersonDTO {
    private Long id;
    private String name;
    private LocalDateTime creationDate;
    private Color eyeColor;
    private Integer height;
    private Country nationality;
}
