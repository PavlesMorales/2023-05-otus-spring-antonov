package ru.otus.spring.homework.dao.book;

import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.book.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookDao {

    List<Book> getAll();

    Book save(Book book);

    void deleteById(Long id);

    Optional<Book> getById(Long id);
}
