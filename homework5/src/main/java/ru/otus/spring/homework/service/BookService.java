package ru.otus.spring.homework.service;

import ru.otus.spring.homework.domain.book.Book;

import java.util.List;

public interface BookService {

    Book createBook(String bookName, Long authorId, Long genreId);

    Book getBook(Long id);

    Book updateBook(Book newBook);

    List<Book> getAllBook();

    void deleteBook(Long id);
}
