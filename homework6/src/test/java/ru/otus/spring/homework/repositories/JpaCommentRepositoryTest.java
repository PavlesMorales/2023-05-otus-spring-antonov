package ru.otus.spring.homework.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework.models.entity.Author;
import ru.otus.spring.homework.models.entity.Book;
import ru.otus.spring.homework.models.entity.Comment;
import ru.otus.spring.homework.models.entity.Genre;
import ru.otus.spring.homework.repositories.impl.JpaCommentRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaCommentRepository.class)
@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
class JpaCommentRepositoryTest {

    @Autowired
    JpaCommentRepository subj;

    List<Comment> dbComment;

    Book dbBook;


    @BeforeEach
    void setUp() {
        dbBook = getDbBook();
        dbComment = getDbComment(dbBook);
    }

    @ParameterizedTest
    @MethodSource("getDbComments")
    @DisplayName("должен загружать комментарии по id")
    void shouldCorrectFindCommentById(Comment expected) {
        final Optional<Comment> actual = subj.findById(expected.getId());

        assertThat(actual)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expected);

    }

    @Test
    @DisplayName("должен загружать все комментарии по id книги")
    void shouldCorrectFindAllByBookId() {
        final List<Comment> expected = getDbComments();
        final List<Comment> actual = subj.findAllByBookId(1L);

        assertThat(actual)
                .isNotEmpty()
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expected);


    }

    @Test
    @DisplayName("должен удалять комментарий по id")
    void shouldDeleteById() {
        final Optional<Comment> commentFromBd = subj.findById(1L);
        assertThat(commentFromBd)
                .isPresent();

        subj.remove(commentFromBd.get());

        final Optional<Comment> expected = subj.findById(1L);
        assertThat(expected)
                .isEmpty();
    }

    @Test
    @DisplayName("должен сохранять новый комментарий")
    void shouldSaveNewComment() {
        final Comment commentNew = new Comment(null, "Comment_999", dbBook);
        final Comment savedComment = subj.save(commentNew);

        assertThat(savedComment)
                .matches(comment -> Objects.nonNull(comment.getId()))
                .usingRecursiveComparison()
                .isEqualTo(commentNew);

        assertThat(subj.findById(savedComment.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(savedComment);

    }


    @Test
    void shouldUpdateComment() {
        final Genre genre = new Genre(2L, "Genre_2");
        final Author author = new Author(2L, "AuthorFirstName_2", "AuthorLastName_2");
        final Book book = new Book(2L, "BookTitle_2", author, genre);

        final Comment expected = new Comment(1L, "Comment_999", book);
        final Optional<Comment> fromDb = subj.findById(1L);

        assertThat(fromDb)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isNotEqualTo(fromDb);

        final Comment savedComment = subj.save(expected);
        assertThat(savedComment)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expected);

    }

    private static List<Comment> getDbComment(Book book) {
        return IntStream.range(1, 6).boxed()
                .map(Long::valueOf)
                .map(id -> new Comment(id, "Comment_" + id, book))
                .toList();
    }

    private static Book getDbBook() {
        final Genre genre = new Genre(1L, "Genre_1");
        final Author author = new Author(1L, "AuthorFirstName_1", "AuthorLastName_1");
        return new Book(1L, "BookTitle_1", author, genre);
    }


    private static List<Comment> getDbComments() {
        return getDbComment(getDbBook());
    }
}