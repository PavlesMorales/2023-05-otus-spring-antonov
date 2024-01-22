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

        Book book = Book.builder()
                .name("BIG BOOK")
                .genre(Genre.builder()
                        .id(1L)
                        .build())
                .author(Author.builder()
                        .id(1L)
                        .build())
                .build();

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

        Optional<Book> actual = subj.create(book);

        assertThat(actual)
                .isPresent()
                .get()
                .isEqualTo(expected);
    }

    @Test
    void getAll() {
        assertThat(subj.getAll()).hasSize(8);
    }

    @Test
    void update() {
        Book book = Book.builder()
                .id(1L)
                .name("BIG BOOK 2")
                .genre(Genre.builder()
                        .id(2L)
                        .build())
                .author(Author.builder()
                        .id(2L)
                        .build())
                .build();

        Book expected = Book.builder()
                .id(1L)
                .name("BIG BOOK 2")
                .genre(Genre.builder()
                        .id(2L)
                        .genreName("Дистопия")
                        .build())
                .author(Author.builder()
                        .id(2L)
                        .firstName("Джордж")
                        .lastName("Оруэлл")
                        .build())
                .build();

        Book bookInDb = Book.builder()
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

        assertThat(subj.getById(1L))
                .isPresent()
                .get()
                .isEqualTo(bookInDb);

        subj.update(book);

        assertThat(subj.getById(1L))
                .isPresent()
                .get()
                .isEqualTo(expected);
    }

    @Test
    void delete() {
        Book bookInDb = Book.builder()
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
        assertThat(subj.getById(1L)).isPresent().get().isEqualTo(bookInDb);

        subj.delete(1L);

        assertThat(subj.getById(1L)).isEmpty();
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

        assertThat(actual)
                .isPresent()
                .get()
                .isEqualTo(expected);
    }
}