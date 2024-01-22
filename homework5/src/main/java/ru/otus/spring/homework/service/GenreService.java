package ru.otus.spring.homework.service;

import ru.otus.spring.homework.domain.genre.Genre;

import java.util.List;

public interface GenreService {

    Genre create(Genre genre);

    List<Genre> getAll();

    void delete(Long id);

    Genre update(Genre genre);

    Genre getById(Long id);
}
