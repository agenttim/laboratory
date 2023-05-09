package ru.dextermedical.laboratory.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.dextermedical.laboratory.models.Person;
import ru.dextermedical.laboratory.repositories.PeopleRepository;
import ru.dextermedical.laboratory.util.PersonErrorResponse;
import ru.dextermedical.laboratory.util.PersonNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeopleService {

    private final PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        return peopleRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    @Transactional
    public void save(Person person) {
        enrichPerson(person);
        peopleRepository.save(person);
    }

    public void delete(int id) {
        Person person = peopleRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException());

        peopleRepository.delete(person);
    }

    public Integer saveAndReturnId(Person person) {
        enrichPerson(person);
        Person savedPerson = peopleRepository.save(person);
        return savedPerson.getId();
    }

    private void enrichPerson(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setCreatedWho("ADMIN");
    }
}
