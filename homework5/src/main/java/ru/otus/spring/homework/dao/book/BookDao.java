package ru.otus.spring.homework.dao.book;

import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.book.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookDao {

    Optional<Book> create(Book book);

    List<Book> getAll();

    void update(Book book);

    void delete(long id);

    Optional<Book> getById(long id);
}
