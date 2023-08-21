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
class GenreDaoImplTest extends TestConfig {

    @Autowired
    GenreDaoImpl subj;

    @Test
    void create() {
        String expectedName = "bookName";
        Optional<Genre> expected = Optional.of(Genre.builder()
                .id(5L)
                .genreName(expectedName)
                .build());
        Optional<Genre> actualOptional = subj.create(expectedName);

        assertThat(actualOptional).isPresent().isEqualTo(expected);

    }

    @Test
    void getAll() {
        Assertions.assertThat(subj.getAll()).hasSize(4);
    }

    @Test
    void update() {
        long id = 1L;
        String expectedName = "bookName";
        Genre expected = Genre.builder()
                .id(id)
                .genreName(expectedName)
                .build();
        Optional<Genre> actual = subj.update(id, expectedName);
        assertThat(actual).isPresent().get().isEqualTo(expected);

    }

    @Test
    void getByName() {
        Genre expected = Genre.builder()
                .id(3L)
                .genreName("Фэнтези")
                .build();
        Optional<Genre> actual = subj.getByName("Фэнтези");
        assertThat(actual).isPresent().get().isEqualTo(expected);

    }

    @Test
    void get() {
        Genre expected = Genre.builder()
                .id(1L)
                .genreName("Роман")
                .build();
        Optional<Genre> actual = subj.get(1L);
        assertThat(actual).isPresent().get().isEqualTo(expected);

    }

    @Test
    void delete() {
        assertThat(subj.delete(1L)).isTrue();
        assertThat(subj.delete(-2)).isFalse();
    }

}