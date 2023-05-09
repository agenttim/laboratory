package ru.dextermedical.laboratory.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.dextermedical.laboratory.dto.PersonDTO;
import ru.dextermedical.laboratory.models.Person;
import ru.dextermedical.laboratory.services.PeopleService;
import ru.dextermedical.laboratory.util.PersonErrorResponse;
import ru.dextermedical.laboratory.util.PersonNotCreatedException;
import ru.dextermedical.laboratory.util.PersonNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin()
public class PersonController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/people")
    public List<PersonDTO> getPeople() {
        return peopleService.findAll().stream().map(this::convertToPersonDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/people/{id}")
    public PersonDTO getPerson(@PathVariable("id") int id) {
        return convertToPersonDTO(peopleService.findOne(id));
    }

    @PostMapping("/people")
    public ResponseEntity<Integer> create(@RequestBody @Valid PersonDTO personDTO,
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

        Integer id = peopleService.saveAndReturnId(convertToPerson(personDTO));

        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/people/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        try {
            peopleService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (PersonNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }
}
