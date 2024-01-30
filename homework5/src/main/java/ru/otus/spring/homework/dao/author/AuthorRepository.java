package ru.otus.spring.homework.dao.author;

import ru.otus.spring.homework.domain.author.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> create(Author author);

    List<Author> getAll();

    void delete(Long id);

    void update(Author author);

    Optional<Author> getById(Long id);
}
