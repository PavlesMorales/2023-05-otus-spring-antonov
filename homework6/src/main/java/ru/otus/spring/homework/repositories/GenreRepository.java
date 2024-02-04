package ru.otus.spring.homework.repositories;

import ru.otus.spring.homework.models.entity.Genre;

import java.util.List;

public interface GenreRepository extends Repository<Genre, Long> {

    List<Genre> findAll();

}
