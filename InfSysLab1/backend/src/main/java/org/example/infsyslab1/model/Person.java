package org.example.infsyslab1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.infsyslab1.model.enums.Color;
import org.example.infsyslab1.model.enums.Country;
import org.hibernate.engine.internal.Cascade;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coordinates_id")
    private Coordinates coordinates;

    @Column(name = "creation_date", nullable = false, updatable = false )
    private LocalDateTime creationDate;

    @Column(name = "eye_color")
    @Enumerated(EnumType.STRING)
    private Color eyeColor;

    @Column(name = "hair_color")
    @Enumerated(EnumType.STRING)
    private Color hairColor;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(nullable = false)
    private Integer height;

    @Column(nullable = true)
    private LocalDate birthday;

    @Column
    @Enumerated(EnumType.STRING)
    private Country nationality;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    private void setCreationDate(){
        this.creationDate = LocalDateTime.now();
    }
}
