package ru.otus.spring.homework.services;

import ru.otus.spring.homework.models.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> findAll();

    GenreDto findById(Long id);

    GenreDto create(GenreDto genreDto);

    GenreDto update(GenreDto genreDto);

    void deleteById(Long id);
}
