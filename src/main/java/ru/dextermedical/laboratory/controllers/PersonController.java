package ru.dextermedical.laboratory.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.dextermedical.laboratory.models.Person;
import ru.dextermedical.laboratory.services.PeopleService;
import ru.dextermedical.laboratory.util.PersonErrorResponse;
import ru.dextermedical.laboratory.util.PersonNotCreatedException;
import ru.dextermedical.laboratory.util.PersonNotFoundException;

import java.util.List;

@RestController
@CrossOrigin()
public class PersonController {

    private final PeopleService peopleService;

    @Autowired
    public PersonController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping("/people")
    public List<Person> getPeople() {
        return peopleService.findAll();
    }

    @GetMapping("/people/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        return peopleService.findOne(id);
    }

    @PostMapping("/people")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Person person,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new PersonNotCreatedException(errorMsg.toString());
        }

        peopleService.save(person);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this id wasn't found!",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
