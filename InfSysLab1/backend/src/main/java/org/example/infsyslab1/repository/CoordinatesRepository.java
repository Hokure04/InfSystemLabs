package org.example.infsyslab1.repository;

import org.example.infsyslab1.model.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
}
