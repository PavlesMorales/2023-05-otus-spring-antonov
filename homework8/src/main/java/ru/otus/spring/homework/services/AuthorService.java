package ru.otus.spring.homework.services;

import ru.otus.spring.homework.models.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAll();

    AuthorDto create(String firstName, String lastName);

    AuthorDto update(String id, String firstName, String lastName);

    void deleteById(String id);

    AuthorDto findById(String id);
}
