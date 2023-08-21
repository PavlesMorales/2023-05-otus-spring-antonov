package ru.otus.spring.homework.domain.book;

import lombok.Builder;
import ru.otus.spring.homework.domain.author.Author;
import ru.otus.spring.homework.domain.genre.Genre;

@Builder(toBuilder = true)
public record Book(long id, String name, Author author, Genre genre) {
}
