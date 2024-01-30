package ru.otus.spring.homework.dao.author;

import org.junit.jupiter.api.Assertions;
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
class JdbcAuthorRepositoryTest extends TestConfig {

    @Autowired
    JdbcAuthorRepository subj;

    @Test
    void create() {
        Author expected = Author.builder()
                .id(8L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();

        Author author = Author.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();

        Optional<Author> actual = subj.create(author);

        assertThat(actual)
                .isPresent()
                .get()
                .isEqualTo(expected);
    }

    @Test
    void getAll() {
        assertThat(subj.getAll()).hasSize(7);
    }

    @Test
    void delete() {
        Optional<Author> byId = subj.getById(1L);
        Assertions.assertTrue(byId.isPresent());
        subj.delete(1L);

        Optional<Author> afterDelete = subj.getById(1L);
        Assertions.assertTrue(afterDelete.isEmpty());

    }

    @Test
    void update() {
        Author authorInDb = Author.builder()
                .id(1L)
                .firstName("Лев")
                .lastName("Толстой")
                .build();

        assertThat(subj.getById(1L))
                .isPresent()
                .get()
                .isEqualTo(authorInDb);

        Author expected = Author.builder()
                .id(1L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();

        subj.update(expected);

        assertThat(subj.getById(1L))
                .isPresent()
                .get()
                .isEqualTo(expected);
    }
}