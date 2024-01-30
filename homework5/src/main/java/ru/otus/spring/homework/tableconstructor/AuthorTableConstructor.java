package ru.otus.spring.homework.tableconstructor;

import ru.otus.spring.homework.domain.author.Author;

import java.util.List;

public interface AuthorTableConstructor {

    String createTable(Author author);

    String createTable(List<Author> author);
}
