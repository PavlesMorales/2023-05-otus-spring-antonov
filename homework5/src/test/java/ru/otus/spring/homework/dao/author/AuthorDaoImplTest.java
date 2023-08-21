package ru.otus.spring.homework.dao.author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.spring.homework.TestConfig;
import ru.otus.spring.homework.domain.author.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql({
        "classpath:sql/drop-tables.sql",
        "classpath:sql/author/create-author-table.sql",
        "classpath:sql/author/author-test-data.sql"
})
class AuthorDaoImplTest extends TestConfig {

    @Autowired
    AuthorDaoImpl subj;

    @Test
    void create() {
        Author expected = Author.builder()
                .id(8L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();

        Optional<Author> actual = subj.create("Ivan", "Ivanov");

        assertThat(actual).isPresent().get().isEqualTo(expected);
    }

    @Test
    void getAll() {
        assertThat(subj.getAll()).hasSize(7);
    }

    @Test
    void delete() {
        assertThat(subj.delete(1L)).isTrue();
        assertThat(subj.delete(999L)).isFalse();
    }

    @Test
    void update() {
        Author expected = Author.builder()
                .id(1L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();

        Optional<Author> actual = subj.update(1, "Ivan", "Ivanov");

        assertThat(actual).isPresent().get().isEqualTo(expected);
    }
}