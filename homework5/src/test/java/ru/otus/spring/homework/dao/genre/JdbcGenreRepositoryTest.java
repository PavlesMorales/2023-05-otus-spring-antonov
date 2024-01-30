package ru.otus.spring.homework.dao.genre;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.spring.homework.TestConfig;
import ru.otus.spring.homework.domain.genre.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql({
        "classpath:sql/drop-tables.sql",
        "classpath:sql/genre/create-genre-table.sql",
        "classpath:sql/genre/genre-test-data.sql"
})
class JdbcGenreRepositoryTest extends TestConfig {

    @Autowired
    JdbcGenreRepository subj;

    @Test
    void create() {
        String expectedName = "genreName";

        Optional<Genre> expected = Optional.of(Genre.builder()
                .id(5L)
                .genreName(expectedName)
                .build());

        Genre genre = Genre.builder()
                .genreName(expectedName)
                .build();

        Optional<Genre> actualOptional = subj.create(genre);

        assertThat(actualOptional).isPresent().isEqualTo(expected);

    }

    @Test
    void getAll() {
        Assertions.assertThat(subj.getAll()).hasSize(4);
    }

    @Test
    void update() {
        long id = 1L;

        String expectedName = "genreName";

        Genre expected = Genre.builder()
                .id(id)
                .genreName(expectedName)
                .build();

        Genre genre = Genre.builder()
                .id(id)
                .genreName("Роман")
                .build();

        assertThat(subj.getById(id))
                .isPresent()
                .get()
                .isEqualTo(genre);

        subj.update(expected);

        assertThat(subj.getById(id))
                .isPresent()
                .get()
                .isEqualTo(expected);

    }

    @Test
    void getByName() {
        Genre expected = Genre.builder()
                .id(3L)
                .genreName("Фэнтези")
                .build();

        Genre genre = Genre.builder()
                .genreName("Фэнтези")
                .build();


        Optional<Genre> actual = subj.getByName(genre);

        assertThat(actual)
                .isPresent()
                .get()
                .isEqualTo(expected);

    }

    @Test
    void get() {
        Genre expected = Genre.builder()
                .id(1L)
                .genreName("Роман")
                .build();

        assertThat(subj.getById(1L))
                .isPresent()
                .get()
                .isEqualTo(expected);

    }

    @Test
    void delete() {
        Genre inDb = Genre.builder()
                .id(3L)
                .genreName("Фэнтези")
                .build();


        assertThat(subj.getById(3L))
                .isPresent()
                .get()
                .isEqualTo(inDb);

        subj.delete(3L);

        assertThat(subj.getById(3L)).isEmpty();

    }

}