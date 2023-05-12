package ru.dextermedical.laboratory.util;

public class PersonNotUpdatedException extends RuntimeException {

    public PersonNotUpdatedException(String msg) {
        super(msg);
    }
}
