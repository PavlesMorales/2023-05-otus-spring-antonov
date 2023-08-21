package ru.otus.spring.homework.tableconstructor;

import ru.otus.spring.homework.domain.genre.Genre;

import java.util.List;

public interface GenreTableConstructor {

    String buildGenreTable(Genre genre);

    String buildGenreTable(List<Genre> genres);
}
