package ru.otus.spring.homework.repositories;

import ru.otus.spring.homework.models.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Optional<Book> findFirstByAuthorId(Long id);

    Optional<Book> findFirstByGenreId(Long id);

    Book save(Book book);

    void deleteById(Long id);

}
