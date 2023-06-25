package ru.otus.spring.homework.exceptions;

public class CsvParserException extends RuntimeException {

    public CsvParserException(String message) {
        super(message);
    }
}
