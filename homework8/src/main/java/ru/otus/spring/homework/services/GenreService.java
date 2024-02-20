package ru.otus.spring.homework.services;

import ru.otus.spring.homework.models.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> findAll();

    GenreDto findById(String id);

    GenreDto create(String name);

    GenreDto update(String id, String name);

    void deleteById(String id);
}
