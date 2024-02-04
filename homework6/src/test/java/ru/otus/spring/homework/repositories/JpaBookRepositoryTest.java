package ru.otus.spring.homework.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework.models.entity.Author;
import ru.otus.spring.homework.models.entity.Book;
import ru.otus.spring.homework.models.entity.Genre;
import ru.otus.spring.homework.repositories.impl.JpaBookRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaBookRepository.class)
@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
class JpaBookRepositoryTest {

    @Autowired
    JpaBookRepository subj;

    List<Author> dbAuthors;

    List<Genre> dbGenres;

    List<Book> dbBooks;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooks = getDbBooks(dbAuthors, dbGenres);
    }

    @ParameterizedTest
    @MethodSource("getDbBooks")
    @DisplayName("должен загружать книгу по id")
    void shouldReturnCorrectBookById(Book expectedBook) {
        final Optional<Book> actualBook = subj.findById(expectedBook.getId());
        assertThat(actualBook)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("должен загружать список всех книг")
    void shouldReturnCorrectBooksList() {
        final List<Book> actualBooks = subj.findAll();
        final List<Book> expectedBooks = dbBooks;

        assertThat(actualBooks)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedBooks);
    }

    @Test
    @DisplayName("должен сохранять новую книгу")
    void shouldSaveNewBook() {
        final Book expectedBook = new Book(null, "BookTitle_10500", dbAuthors.get(0), dbGenres.get(0));
        final Book savedBook = subj.save(expectedBook);

        assertThat(savedBook)
                .isNotNull()
                .matches(book -> Objects.nonNull(book.getId()))
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedBook);

        assertThat(subj.findById(savedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(savedBook);
    }

    @Test
    @DisplayName("должен сохранять измененную книгу")
    @Transactional
    void shouldSaveUpdatedBook() {
        final Book expectedBook = new Book(1L, "BookTitle_10500", dbAuthors.get(2), dbGenres.get(2));

        final Optional<Book> bookFromDb = subj.findById(expectedBook.getId());

        assertThat(bookFromDb)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isNotEqualTo(expectedBook);

        final Book savedBook = subj.save(expectedBook);

        assertThat(savedBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("должен удалять книгу по id ")
    void shouldDeleteBook() {
        final Optional<Book> existingBook = subj.findById(1L);
        assertThat(existingBook).isPresent();
        subj.remove(existingBook.get());
        assertThat(subj.findById(1L)).isEmpty();
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(Long::valueOf)
                .map(id -> new Author(id, "AuthorFirstName_" + id, "AuthorLastName_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(Long::valueOf)
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(Long::valueOf)
                .map(id -> new Book(id, "BookTitle_" + id, dbAuthors.get(id.intValue() - 1), dbGenres.get(id.intValue() - 1)))
                .toList();
    }

    private static List<Book> getDbBooks() {
        final List<Author> dbAuthors = getDbAuthors();
        final List<Genre> dbGenres = getDbGenres();
        return getDbBooks(dbAuthors, dbGenres);
    }
}