package ru.otus.spring.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.dao.book.BookDao;
import ru.otus.spring.homework.domain.book.Book;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Override
    public Optional<Book> createBook(String bookName, long authorId, long genreId) {
        return bookDao.create(bookName, authorId, genreId);

    }

    @Override
    public Optional<Book> getBook(long id) {
        return bookDao.getById(id);
    }

    @Override
    public Optional<Book> updateName(long id, String newName) {
        return bookDao.update(id, newName);
    }

    @Override
    public List<Book> getAllBook() {
        return bookDao.getAll();
    }

    @Override
    public boolean deleteBook(long id) {
        return bookDao.delete(id);
    }
}
