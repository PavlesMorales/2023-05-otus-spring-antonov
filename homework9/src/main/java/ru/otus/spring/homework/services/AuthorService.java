package ru.otus.spring.homework.services;

import ru.otus.spring.homework.models.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAll();

    AuthorDto create(AuthorDto authorDto);

    AuthorDto update(AuthorDto authorDto);

    void deleteById(Long id);

    AuthorDto findById(Long id);
}
