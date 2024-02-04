package ru.otus.spring.homework.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework.models.entity.Author;
import ru.otus.spring.homework.repositories.impl.JpaAuthorRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuthorRepository.class)
@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
class JpaAuthorRepositoryTest {

    @Autowired
    JpaAuthorRepository subj;

    List<Author> dbAuthors;


    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
    }

    @ParameterizedTest
    @MethodSource("getDbAuthors")
    @DisplayName("должен загружать автора по id")
    void shouldCorrectReturnById(Author expected) {
        final Optional<Author> actual = subj.findById(expected.getId());

        assertThat(actual)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("должен загружать список всех авторов")
    void shouldReturnCorrectAuthorsList() {
        final List<Author> actualAuthors = subj.findAll();
        final List<Author> expectedAuthors = dbAuthors;

        assertThat(actualAuthors)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedAuthors);
    }

    @Test
    @DisplayName("Должен сохранять нового автора")
    void shouldSaveNewAuthor() {
        final Author expectedAuthor = new Author(null, "AuthorFirstName_999", "AuthorLastName_999");
        final Author savedAuthor = subj.save(expectedAuthor);

        assertThat(savedAuthor)
                .isNotNull()
                .matches(author -> Objects.nonNull(author.getId()))
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedAuthor);

        assertThat(subj.findById(savedAuthor.getId()))
                .isPresent()
                .get()
                .isEqualTo(savedAuthor);
    }

    @Test
    @DisplayName("должен сохранять измененную книгу")
    void shouldSaveUpdatedAuthor() {
        final Author expectedAuthor = new Author(1L, "AuthorFirstName_999", "AuthorLastName_999");

        final Optional<Author> authorFromBd = subj.findById(expectedAuthor.getId());

        assertThat(authorFromBd)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isNotEqualTo(expectedAuthor);

        final Author savedAuthor = subj.save(expectedAuthor);
        assertThat(savedAuthor.getId())
                .isNotNull()
                .isEqualTo(expectedAuthor.getId());

        final Optional<Author> savedAuthorFromBd = subj.findById(savedAuthor.getId());
        assertThat(savedAuthorFromBd)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);

    }

    @Test
    @DisplayName("должен удалять автора по id ")
    void shouldDeleteAuthorById() {
        final Optional<Author> existingAuthor = subj.findById(1L);
        assertThat(existingAuthor).isPresent();
        subj.remove(existingAuthor.get());
        assertThat(subj.findById(1L)).isEmpty();
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(Long::valueOf)
                .map(id -> new Author(id, "AuthorFirstName_" + id, "AuthorLastName_" + id))
                .toList();
    }
}