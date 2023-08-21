package ru.otus.spring.homework.dao.author;

import ru.otus.spring.homework.domain.author.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Optional<Author> create(String firstName, String lastName);

    List<Author> getAll();

    boolean delete(long id);

    Optional<Author> update(long id, String firstName, String lastName);

    Optional<Author> getById(Long id);
}
