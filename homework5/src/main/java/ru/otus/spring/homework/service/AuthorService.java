package ru.otus.spring.homework.service;

import ru.otus.spring.homework.domain.author.Author;

import java.util.List;

public interface AuthorService {

    Author create(Author author);

    List<Author> getAll();

    void delete(long id);

    Author update(Author author);

    Author getById(Long id);
}
