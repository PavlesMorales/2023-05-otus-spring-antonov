package ru.otus.spring.homework.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework.models.entity.Author;
import ru.otus.spring.homework.repositories.impl.JpaAuthorRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuthorRepository.class)
@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
class JpaAuthorRepositoryTest {

    @Autowired
    JpaAuthorRepository subj;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    @DisplayName("должен загружать автора по id")
    void shouldCorrectReturnById() {
        final Author expected = testEntityManager.find(Author.class, 1L);
        final Optional<Author> actual = subj.findById(1L);

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
        final List<Author> expectedAuthors = testEntityManager.getEntityManager()
                .createQuery("select a from Author a", Author.class).getResultList();

        assertThat(actualAuthors)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedAuthors);
    }

    @Test
    @DisplayName("Должен сохранять нового автора")
    void shouldSaveNewAuthor() {
        final Author expectedAuthor = new Author(null, "AuthorFirstName_999", "AuthorLastName_999");
        final Author savedAuthor = subj.save(expectedAuthor);

        final Author expected = testEntityManager.find(Author.class, savedAuthor.getId());
        assertThat(savedAuthor)
                .isNotNull()
                .matches(author -> Objects.nonNull(author.getId()))
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("должен сохранять измененную книгу")
    void shouldSaveUpdatedAuthor() {
        final Author expectedAuthor = new Author(1L, "AuthorFirstName_999", "AuthorLastName_999");

        final Author authorFromBd = testEntityManager.find(Author.class, 1L);

        assertThat(authorFromBd)
                .usingRecursiveComparison()
                .isNotEqualTo(expectedAuthor);

        final Author savedAuthor = subj.save(expectedAuthor);
        assertThat(savedAuthor.getId())
                .isNotNull()
                .isEqualTo(expectedAuthor.getId());

        final Author savedAuthorFromBd = testEntityManager.find(Author.class, 1L);
        assertThat(savedAuthorFromBd)
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);

    }

    @Test
    @DisplayName("должен удалять автора по id ")
    void shouldDeleteAuthorById() {
        final Author authorFromBd = testEntityManager.find(Author.class, 1L);

        assertThat(authorFromBd).isNotNull();
        subj.deleteById(1L);

        final Author authorFromBdAfterDelete = testEntityManager.find(Author.class, 1L);

        assertThat(authorFromBdAfterDelete).isNull();
    }
}