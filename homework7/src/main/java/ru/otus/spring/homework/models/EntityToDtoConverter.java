package ru.otus.spring.homework.models;

public interface EntityToDtoConverter<S, T> {

    T convert(S source);
}
