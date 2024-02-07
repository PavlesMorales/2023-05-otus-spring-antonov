package ru.otus.spring.homework.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.homework.models.entity.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
class JpaBookRepositoryTest {

    @Autowired
    BookRepository subj;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    @DisplayName("должен загружать список всех книг")
    void shouldReturnCorrectBooksList() {
        final List<Book> actualBooks = subj.findAll();
        final List<Book> expectedBooks = testEntityManager.getEntityManager()
                .createQuery("""
                        select b from Book b
                            left join fetch b.genre
                            left join fetch b.author
                        """, Book.class)
                .getResultList();

        assertThat(actualBooks)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedBooks);
    }

    @Test
    @DisplayName("должен загружать книгу по id")
    void shouldReturnBookById(){
        final Book expected = testEntityManager.find(Book.class, 1L);
        final Optional<Book> actual = subj.findById(1L);
        assertThat(actual)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}