package ru.otus.spring.homework.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.spring.homework.ApplicationTestConfig;

import static org.assertj.core.api.Assertions.assertThat;


class BookRepositoryTest extends ApplicationTestConfig {

    @Autowired
    BookRepository subj;

    @Test
    void shouldReturnTrueBecauseBookExistingWithAuthorId() {
        assertThat(subj.existsByAuthorId("000000000000000000000001")).isTrue();
    }

    @Test
    void shouldReturnFalseBecauseBookNonExistingWithAuthorId() {
        assertThat(subj.existsByAuthorId("000000000000000000000099")).isFalse();
    }


    @Test
    void shouldReturnTrueBecauseBookExistingWithGenreId() {
        assertThat(subj.existsByAuthorId("000000000000000000000001")).isTrue();
    }

    @Test
    void shouldReturnFalseBecauseBookNotExistingWithGenreId() {
        assertThat(subj.existsByAuthorId("0000000000000000000000099")).isFalse();
    }
}