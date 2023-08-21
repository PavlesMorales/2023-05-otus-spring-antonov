package ru.otus.spring.homework.dao.book;

import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.book.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookDao {

    Optional<Book> create(String bookName, long authorId, long genreId);

    List<Book> getAll();

    Optional<Book> update(long id, String newName);

    boolean delete(long id);

    Optional<Book> getById(long id);
}