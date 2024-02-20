package ru.otus.spring.homework.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookEntityToDtoConverter converter;

    @Override
    @Transactional(readOnly = true)
    public BookDto findById(Long id) {
        return converter.convert(getBook(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(converter::convert)
                .toList();
    }

    @Override
    @Transactional
    public BookDto create(final BookDto bookDto) {
        final Author author = getAuthor(bookDto.getAuthorDto().getId());
        final Genre genre = getGenre(bookDto.getGenreDto().getId());

        final Book book = Book.builder()
                .title(bookDto.getTitle())
                .author(author)
                .genre(genre)
                .build();
        return converter.convert(bookRepository.save(book));
    }

    @Override
    @Transactional
    public BookDto update(final BookDto bookDto) {
        final Book book = getBook(bookDto.getId());
        final Author author = getAuthor(bookDto.getAuthorDto().getId());
        final Genre genre = getGenre(bookDto.getGenreDto().getId());

        book.setTitle(bookDto.getTitle());
        book.setAuthor(author);
        book.setGenre(genre);

        return converter.convert(bookRepository.save(book));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    private Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
    }

    private Author getAuthor(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(id)));
    }

    private Genre getGenre(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %d not found".formatted(id)));
    }
}
