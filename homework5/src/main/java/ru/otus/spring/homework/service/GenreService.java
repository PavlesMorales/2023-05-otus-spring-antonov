package ru.otus.spring.homework.service;

import ru.otus.spring.homework.domain.genre.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<Genre> createGenre(String genre);

    List<Genre> getAll();

    boolean delete(long id);

    Optional<Genre> update(long id, String newName);

    Optional<Genre> get(long id);
}
