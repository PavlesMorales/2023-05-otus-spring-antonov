package ru.otus.spring.homework.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework.models.entity.Genre;
import ru.otus.spring.homework.repositories.impl.JpaGenreRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaGenreRepository.class)
@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
class JpaGenreRepositoryTest {

    @Autowired
    JpaGenreRepository subj;

    List<Genre> dbGenres;


    @BeforeEach
    void setUp() {
        dbGenres = getDbGenres();
    }

    @ParameterizedTest
    @MethodSource("getDbGenres")
    @DisplayName("должен загружать жанр по id")
    void shouldReturnCorrectGenreById(Genre expected) {
        final Optional<Genre> actual = subj.findById(expected.getId());

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
        final List<Genre> expectedList = dbGenres;
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

        assertThat(subj.findById(savedGenre.getId()))
                .isPresent()
                .get()
                .isEqualTo(savedGenre);
    }

    @Test
    @DisplayName("должен сохранять измененый жанр")
    void shouldSaveUpdatedGenre() {
        final Genre expectedGenre = new Genre(1L, "Genre_999");

        final Optional<Genre> genreFromDb = subj.findById(expectedGenre.getId());
        assertThat(genreFromDb)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isNotEqualTo(expectedGenre);

        final Genre savedGenre = subj.save(expectedGenre);
        assertThat(savedGenre.getId())
                .isNotNull()
                .isEqualTo(expectedGenre.getId());

        final Optional<Genre> updatedGenreFromBd = subj.findById(savedGenre.getId());
        assertThat(updatedGenreFromBd)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("должен удалять жанр по id ")
    void shouldDeleteGenreById() {
        final Optional<Genre> existingGenre = subj.findById(1L);
        assertThat(existingGenre).isPresent();
        subj.remove(existingGenre.get());
        assertThat(subj.findById(1L)).isEmpty();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(Long::valueOf)
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }
}