package org.example.infsyslab1.repository;

import org.example.infsyslab1.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByUserId(Long userId);
}
