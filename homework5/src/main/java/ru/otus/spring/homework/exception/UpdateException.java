package ru.otus.spring.homework.exception;

public class UpdateException extends RuntimeException {

    public UpdateException(final String message) {
        super(message);
    }
}
