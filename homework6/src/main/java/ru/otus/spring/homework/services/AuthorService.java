package ru.otus.spring.homework.services;

import ru.otus.spring.homework.models.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAll();

    AuthorDto create(String firstName, String lastName);

    AuthorDto update(Long id, String firstName, String lastName);

    void deleteById(Long id);

    AuthorDto findById(Long id);
}
