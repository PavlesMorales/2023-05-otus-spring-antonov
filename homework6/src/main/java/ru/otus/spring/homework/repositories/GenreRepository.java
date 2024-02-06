package ru.otus.spring.homework.repositories;

import ru.otus.spring.homework.models.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    List<Genre> findAll();

    Optional<Genre> findById(Long id);

    Genre save(Genre entity);

    void deleteById(Long id);

}
