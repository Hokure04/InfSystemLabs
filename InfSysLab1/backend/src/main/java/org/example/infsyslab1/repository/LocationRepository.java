package org.example.infsyslab1.repository;

import org.example.infsyslab1.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
