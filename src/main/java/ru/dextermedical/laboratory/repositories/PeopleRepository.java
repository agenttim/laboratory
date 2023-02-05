package ru.dextermedical.laboratory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dextermedical.laboratory.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
