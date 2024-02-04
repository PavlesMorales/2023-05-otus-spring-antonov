package ru.otus.spring.homework.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework.models.entity.Author;
import ru.otus.spring.homework.models.entity.Book;
import ru.otus.spring.homework.models.entity.Genre;
import ru.otus.spring.homework.repositories.impl.JpaBookRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaBookRepository.class)
@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
class JpaBookRepositoryTest {

    @Autowired
    JpaBookRepository subj;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    @DisplayName("должен загружать книгу по id")
    void shouldReturnCorrectBookById() {
        final Book expected = testEntityManager.find(Book.class, 1L);
        final Optional<Book> actualBook = subj.findById(1L);

        assertThat(actualBook)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("должен загружать список всех книг")
    void shouldReturnCorrectBooksList() {
        final List<Book> actualBooks = subj.findAll();
        final List<Book> expectedBooks = testEntityManager.getEntityManager()
                .createQuery("""
                        select b from Book b
                        left join fetch b.author
                        left join fetch b.genre
                        """, Book.class)
                .getResultList();

        assertThat(actualBooks)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedBooks);
    }

    @Test
    @DisplayName("должен сохранять новую книгу")
    void shouldSaveNewBook() {
        final Author author = testEntityManager.find(Author.class, 1L);
        final Genre genre = testEntityManager.find(Genre.class, 1L);
        final Book expectedBook = new Book(null, "BookTitle_10500", author, genre);
        final Book actualBook = subj.save(expectedBook);

        assertThat(actualBook)
                .isNotNull()
                .matches(book -> Objects.nonNull(book.getId()))
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedBook);
        final Book expected = testEntityManager.find(Book.class, actualBook.getId());

        assertThat(actualBook)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("должен сохранять измененную книгу")
    @Transactional
    void shouldSaveUpdatedBook() {
        final Author author = testEntityManager.find(Author.class, 2L);
        final Genre genre = testEntityManager.find(Genre.class, 2L);
        final Book book = new Book(1L, "BookTitle_10500", author, genre);

        final Book bookFromDb = testEntityManager.find(Book.class, 1L);
        assertThat(book)
                .usingRecursiveComparison()
                .isNotEqualTo(bookFromDb);

        final Book actualBook = subj.save(book);
        final Book expectedBook = testEntityManager.find(Book.class, 1L);

        assertThat(actualBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("должен удалять книгу по id ")
    void shouldDeleteBook() {
        final Book existingBook = testEntityManager.find(Book.class, 1L);
        assertThat(existingBook).isNotNull();

        subj.remove(existingBook);

        assertThat(testEntityManager.find(Book.class, 1L)).isNull();
    }
}