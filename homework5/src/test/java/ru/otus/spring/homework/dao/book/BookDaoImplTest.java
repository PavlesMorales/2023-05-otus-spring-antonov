package ru.otus.spring.homework.dao.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.spring.homework.TestConfig;
import ru.otus.spring.homework.domain.author.Author;
import ru.otus.spring.homework.domain.book.Book;
import ru.otus.spring.homework.domain.genre.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Sql({
        "classpath:sql/drop-tables.sql",
        "classpath:sql/book/create-tables.sql",
        "classpath:sql/book/book-test-data.sql"
})
class BookDaoImplTest extends TestConfig {

    @Autowired
    BookDaoImpl subj;

    @Test
    void create() {
        Book expected = Book.builder()
                .id(9L)
                .name("BIG BOOK")
                .genre(Genre.builder()
                        .id(1L)
                        .genreName("Роман")
                        .build())
                .author(Author.builder()
                        .id(1L)
                        .firstName("Лев")
                        .lastName("Толстой")
                        .build())
                .build();

        Optional<Book> actual = subj.create("BIG BOOK", 1, 1);

        assertThat(actual).isPresent().get().isEqualTo(expected);
    }

    @Test
    void getAll() {
        assertThat(subj.getAll()).hasSize(8);
    }

    @Test
    void update() {
        Book expected = Book.builder()
                .id(1L)
                .name("BIG BOOK 2")
                .genre(Genre.builder()
                        .id(1L)
                        .genreName("Роман")
                        .build())
                .author(Author.builder()
                        .id(1L)
                        .firstName("Лев")
                        .lastName("Толстой")
                        .build())
                .build();

        Optional<Book> actual = subj.update(1, "BIG BOOK 2");

        assertThat(actual).isPresent().get().isEqualTo(expected);
    }

    @Test
    void delete() {
    }

    @Test
    void getById() {
        Book expected = Book.builder()
                .id(1L)
                .name("Война и мир")
                .genre(Genre.builder()
                        .id(1L)
                        .genreName("Роман")
                        .build())
                .author(Author.builder()
                        .id(1L)
                        .firstName("Лев")
                        .lastName("Толстой")
                        .build())
                .build();

        Optional<Book> actual = subj.getById(1L);

        assertThat(actual).isPresent().get().isEqualTo(expected);
    }
}