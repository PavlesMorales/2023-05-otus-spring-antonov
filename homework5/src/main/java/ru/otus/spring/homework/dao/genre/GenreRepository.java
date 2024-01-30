package ru.otus.spring.homework.dao.genre;

import ru.otus.spring.homework.domain.genre.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    Optional<Genre> create(Genre genre);

    List<Genre> getAll();

    void update(Genre genre);

    void delete(long id);

    Optional<Genre> getByName(Genre genre);

    Optional<Genre> getById(long id);
}
