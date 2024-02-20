package ru.otus.spring.homework.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.spring.homework.ApplicationTestConfig;
import ru.otus.spring.homework.changelog.ChangeLogTest;
import ru.otus.spring.homework.models.entity.Book;
import ru.otus.spring.homework.models.entity.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentRepositoryTest extends ApplicationTestConfig {

    @Autowired
    CommentRepository repository;

    List<Comment> comments;

    Book book;

    @BeforeEach
    void init() {
        book = ChangeLogTest.getBooks(ChangeLogTest.getAuthors(), ChangeLogTest.getGenres()).get(0);
        comments = ChangeLogTest.getComments(book);
    }

    @Test
    @Order(1)
    void shouldFindAllCommentsByBookId() {
        final List<Comment> comments =
                ChangeLogTest.getComments(book);

        final List<Comment> expected = repository.findAllByBookId(book.getId());

        assertThat(expected)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("book")
                .containsExactlyElementsOf(comments);
    }

    @Test
    @Order(2)
    void shouldDeleteAllCommentsByBookId() {
        final List<Comment> allByBookId = repository.findAllByBookId(book.getId());
        assertThat(allByBookId)
                .isNotEmpty();

        repository.deleteAllByBookId(book.getId());

        final List<Comment> afterDeletion = repository.findAllByBookId(book.getId());

        assertThat(afterDeletion)
                .isEmpty();
    }
}