package ru.otus.spring.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.dao.author.AuthorDao;
import ru.otus.spring.homework.dao.book.BookDao;
import ru.otus.spring.homework.dao.genre.GenreDao;
import ru.otus.spring.homework.domain.author.Author;
import ru.otus.spring.homework.domain.book.Book;
import ru.otus.spring.homework.domain.genre.Genre;
import ru.otus.spring.homework.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    @Override
    public Book createBook(final String bookName, final Long authorId, final Long genreId) {
        final Author author = authorDao.getById(authorId)
                .orElseThrow(() -> new NotFoundException("Author", authorId));
        final Genre genre = genreDao.getById(genreId)
                .orElseThrow(() -> new NotFoundException("Genre", genreId));

        return bookDao.save(Book.builder()
                        .author(author)
                        .genre(genre)
                        .name(bookName)
                        .build());
    }

    @Override
    public Book getBook(final Long id) {
        return bookDao.getById(id)
                .orElseThrow(() -> new NotFoundException("Book", id));
    }

    @Override
    public Book updateBook(final Book book) {
        final Book bookFromDb = bookDao.getById(book.id()).orElseThrow(() -> new NotFoundException("Book", book.id()));
        final Book.BookBuilder bookBuilder = bookFromDb.toBuilder()
                .name(book.name());

        Optional.of(book.author())
                .map(Author::id)
                .ifPresent(id -> {
                    final Author author = authorDao.getById(book.author().id())
                            .orElseThrow(() -> new NotFoundException("Author", book.author().id()));
                    bookBuilder.author(author);
                });

        Optional.of(book.genre())
                .map(Genre::id)
                .ifPresent(id -> {
                    final Genre genre = genreDao.getById(book.genre().id())
                            .orElseThrow(() -> new NotFoundException("Genre", book.genre().id()));
                    bookBuilder.genre(genre);
                });

        return bookDao.save(bookBuilder.build());
    }

    @Override
    public List<Book> getAllBook() {
        return bookDao.getAll();
    }

    @Override
    public void deleteBook(final Long id) {
        bookDao.deleteById(id);
    }
}
