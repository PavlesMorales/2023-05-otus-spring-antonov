package ru.otus.spring.homework.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.exceptions.EntityNotFoundException;
import ru.otus.spring.homework.models.converters.BookEntityToDtoConverter;
import ru.otus.spring.homework.models.dto.BookDto;
import ru.otus.spring.homework.models.entity.Author;
import ru.otus.spring.homework.models.entity.Book;
import ru.otus.spring.homework.models.entity.Genre;
import ru.otus.spring.homework.repositories.AuthorRepository;
import ru.otus.spring.homework.repositories.BookRepository;
import ru.otus.spring.homework.repositories.GenreRepository;
import ru.otus.spring.homework.services.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookEntityToDtoConverter converter;

    @Override
    public BookDto findById(String id) {
        return converter.convert(getBook(id));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(converter::convert)
                .toList();
    }

    @Override
    public BookDto create(final String title, final String authorId, final String genreId) {
        final Author author = getAuthor(authorId);
        final Genre genre = getGenre(genreId);

        final Book book = Book.builder()
                .title(title)
                .author(author)
                .genre(genre)
                .build();
        return converter.convert(bookRepository.save(book));
    }

    @Override
    public BookDto update(final String bookId, final String title, final String authorId, final String genreId) {
        final Book book = getBook(bookId);
        final Author author = getAuthor(authorId);
        final Genre genre = getGenre(genreId);

        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);

        return converter.convert(bookRepository.save(book));
    }

    @Override
    public void deleteById(final String id) {
        bookRepository.deleteById(id);
    }

    private Book getBook(final String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(id)));
    }

    private Author getAuthor(final String id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %s not found".formatted(id)));
    }

    private Genre getGenre(final String id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %s not found".formatted(id)));
    }
}
