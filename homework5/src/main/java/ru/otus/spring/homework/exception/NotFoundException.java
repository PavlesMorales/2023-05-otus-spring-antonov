package ru.otus.spring.homework.exception;

public class NotFoundException extends RuntimeException {

    private static final String NOT_FOUND_MESSAGE_TEMPLATE = "%s not found by id: %s";

    public NotFoundException(String type, Long id) {
        super(NOT_FOUND_MESSAGE_TEMPLATE.formatted(type, id));
    }
}
