package org.example.infsyslab1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coordinates")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Coordinates {

    @Id
    @Column(name = "coordinates_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer x;

    @Column
    private float y;

    @PrePersist
    @PreUpdate
    private void validateX(){
        if( x <= - 646){
            throw new IllegalArgumentException("Значение x должно быть больше -646");
        }
    }
}
