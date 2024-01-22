package ru.otus.spring.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.dao.author.AuthorDao;
import ru.otus.spring.homework.dao.book.BookDao;
import ru.otus.spring.homework.dao.genre.GenreDao;
import ru.otus.spring.homework.domain.author.Author;
import ru.otus.spring.homework.domain.book.Book;
import ru.otus.spring.homework.domain.genre.Genre;
import ru.otus.spring.homework.exception.CreationException;
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

        return bookDao.create(Book.builder()
                        .author(Author
                                .builder()
                                .id(author.id())
                                .build())
                        .genre(Genre
                                .builder()
                                .id(genre.id())
                                .build())
                        .name(bookName)
                        .build())
                .orElseThrow(() -> new CreationException("Error when creating book"));

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

        final Book updatedBook = bookBuilder.build();
        bookDao.update(updatedBook);
        return updatedBook;
    }

    @Override
    public List<Book> getAllBook() {
        return bookDao.getAll();
    }

    @Override
    public void deleteBook(final Long id) {
        bookDao.delete(id);
    }
}
