package ru.dextermedical.laboratory.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Column(name = "surname")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Surname should be between 2 and 30 characters")
    private String surname;

/*    @Column(name = "date_of_birth")
    @NotEmpty(message = "Date should not be empty")
    private LocalDateTime dateOfBirth;*/

    @Column(name = "email")
    @Email
    @NotEmpty(message = "Email should not be empty")
    private String email;

    public Person() {
    }

    public Person(String name, String surname, LocalDateTime dateOfBirth, String email) {
        this.name = name;
        this.surname = surname;
        //this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

/*    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }*/

/*    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                /*", dateOfBirth=" + dateOfBirth +*/
                ", email='" + email + '\'' +
                '}';
    }
}
