package ru.otus.spring.homework.dao.genre;

import ru.otus.spring.homework.domain.genre.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    Optional<Genre> create(String genreName);

    List<Genre> getAll();

    Optional<Genre> update(long id, String genreName);

    boolean delete(long id);

    Optional<Genre> getByName(String genreName);

    Optional<Genre> get(long id);
}
