package ru.otus.spring.homework.exceptions;

public class EntityDeleteException extends RuntimeException {

    public EntityDeleteException(final String message) {
        super(message);
    }
}
