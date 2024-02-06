package ru.otus.spring.homework.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework.models.entity.Genre;
import ru.otus.spring.homework.repositories.impl.JpaGenreRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaGenreRepository.class)
@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
class JpaGenreRepositoryTest {

    @Autowired
    JpaGenreRepository subj;

    @Autowired
    TestEntityManager testEntityManager;


    @Test
    @DisplayName("должен загружать жанр по id")
    void shouldReturnCorrectGenreById() {
        final Genre expected = testEntityManager.find(Genre.class, 1L);
        final Optional<Genre> actual = subj.findById(1L);

        assertThat(actual)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("должен загружать список всех жанров")
    void shouldReturnCorrectGenreList() {
        final List<Genre> actualList = subj.findAll();
        final List<Genre> expectedList = testEntityManager.getEntityManager()
                .createQuery("select g from Genre g", Genre.class)
                .getResultList();

        assertThat(actualList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedList);
    }

    @Test
    @DisplayName("должен сохранять новый жанр")
    void shouldSaveNewGenre() {
        final Genre expectedGenre = new Genre(null, "Genre_999");
        final Genre savedGenre = subj.save(expectedGenre);

        assertThat(savedGenre)
                .isNotNull()
                .matches(genre -> Objects.nonNull(genre.getId()))
                .usingRecursiveComparison()
                .isEqualTo(expectedGenre);

        final Genre expected = testEntityManager.find(Genre.class, savedGenre.getId());
        assertThat(savedGenre)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("должен сохранять измененый жанр")
    void shouldSaveUpdatedGenre() {
        final Genre genre = new Genre(1L, "Genre_999");
        final Genre genreFromDb = testEntityManager.find(Genre.class, genre.getId());

        assertThat(genreFromDb)
                .usingRecursiveComparison()
                .isNotEqualTo(genre);

        final Genre savedGenre = subj.save(genre);
        final Genre expected = testEntityManager.find(Genre.class, genre.getId());

        assertThat(savedGenre)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expected);

    }

    @Test
    @DisplayName("должен удалять жанр по id ")
    void shouldDeleteGenreById() {
        final Genre existingGenre = testEntityManager.find(Genre.class, 1L);
        assertThat(existingGenre).isNotNull();

        subj.deleteById(1L);

        final Genre removedGenre = testEntityManager.find(Genre.class, 1L);
        assertThat(removedGenre).isNull();
    }
}