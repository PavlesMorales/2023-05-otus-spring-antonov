package ru.otus.spring.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.dao.author.AuthorRepository;
import ru.otus.spring.homework.dao.book.BookRepository;
import ru.otus.spring.homework.dao.genre.GenreRepository;
import ru.otus.spring.homework.domain.author.Author;
import ru.otus.spring.homework.domain.book.Book;
import ru.otus.spring.homework.domain.genre.Genre;
import ru.otus.spring.homework.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @Override
    public Book createBook(final String bookName, final Long authorId, final Long genreId) {
        final Author author = authorRepository.getById(authorId)
                .orElseThrow(() -> new NotFoundException("Author", authorId));
        final Genre genre = genreRepository.getById(genreId)
                .orElseThrow(() -> new NotFoundException("Genre", genreId));

        return bookRepository.save(Book.builder()
                        .author(author)
                        .genre(genre)
                        .name(bookName)
                        .build());
    }

    @Override
    public Book getBook(final Long id) {
        return bookRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("Book", id));
    }

    @Override
    public Book updateBook(final Book book) {
        final Book bookFromDb = bookRepository.getById(book.id())
                .orElseThrow(() -> new NotFoundException("Book", book.id()));
        final Book.BookBuilder bookBuilder = bookFromDb.toBuilder()
                .name(book.name());

        Optional.of(book.author())
                .map(Author::id)
                .ifPresent(id -> {
                    final Author author = authorRepository.getById(book.author().id())
                            .orElseThrow(() -> new NotFoundException("Author", book.author().id()));
                    bookBuilder.author(author);
                });

        Optional.of(book.genre())
                .map(Genre::id)
                .ifPresent(id -> {
                    final Genre genre = genreRepository.getById(book.genre().id())
                            .orElseThrow(() -> new NotFoundException("Genre", book.genre().id()));
                    bookBuilder.genre(genre);
                });

        return bookRepository.save(bookBuilder.build());
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.getAll();
    }

    @Override
    public void deleteBook(final Long id) {
        bookRepository.deleteById(id);
    }
}
