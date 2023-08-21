package ru.otus.spring.homework.service;

import ru.otus.spring.homework.domain.book.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> createBook(String bookName, long authorId, long genreId);

    Optional<Book> getBook(long id);

    Optional<Book> updateName(long id, String newName);

    List<Book> getAllBook();

    boolean deleteBook(long id);
}
