package ru.otus.spring.homework.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.homework.models.entity.Book;
import ru.otus.spring.homework.models.entity.Comment;
import ru.otus.spring.homework.repositories.impl.JpaCommentRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaCommentRepository.class)
@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
class JpaCommentRepositoryTest {

    @Autowired
    JpaCommentRepository subj;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    @DisplayName("должен загружать комментарии по id")
    void shouldCorrectFindCommentById() {
        final Comment expected = testEntityManager.find(Comment.class, 1L);
        final Optional<Comment> actual = subj.findById(1L);

        assertThat(actual)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expected);

    }

    @Test
    @DisplayName("должен загружать все комментарии по id книги")
    void shouldCorrectFindAllByBookId() {
        final Long bookId = 1L;
        final List<Comment> expected = testEntityManager.getEntityManager()
                .createQuery(
                        """
                                select c from Comment c
                                    left join fetch c.book b
                                    left join fetch b.author
                                    left join fetch b.genre
                                where b.id =:bookId
                                """, Comment.class)
                .setParameter("bookId", bookId)
                .getResultList();

        final List<Comment> actual = subj.findAllByBookId(bookId);

        assertThat(actual)
                .isNotEmpty()
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expected);


    }

    @Test
    @DisplayName("должен удалять комментарий по id")
    void shouldDeleteById() {
        final Comment existingComment = testEntityManager.find(Comment.class, 1L);
        assertThat(existingComment)
                .isNotNull();

        subj.deleteById(1L);

        final Comment removedComment = testEntityManager.find(Comment.class, 1L);
        assertThat(removedComment)
                .isNull();
    }

    @Test
    @DisplayName("должен сохранять новый комментарий")
    void shouldSaveNewComment() {
        final Book book = testEntityManager.find(Book.class, 1L);
        final Comment commentNew = new Comment(null, "Comment_999", book);

        final Comment savedComment = subj.save(commentNew);

        assertThat(savedComment)
                .matches(comment -> Objects.nonNull(comment.getId()))
                .usingRecursiveComparison()
                .isEqualTo(commentNew);

        assertThat(testEntityManager.find(Comment.class, savedComment.getId()))
                .usingRecursiveComparison()
                .isEqualTo(savedComment);

    }


    @Test
    @DisplayName("должен обновлять существующий комментарий")
    void shouldUpdateComment() {
        final Book book = testEntityManager.find(Book.class, 1L);
        final Comment comment = new Comment(1L, "Comment_999", book);

        final Comment fromDb = testEntityManager.find(Comment.class, 1L);
        assertThat(fromDb)
                .usingRecursiveComparison()
                .isNotEqualTo(comment);

        final Comment savedComment = subj.save(comment);

        final Comment expected = testEntityManager.find(Comment.class, 1L);
        assertThat(savedComment)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expected);

    }
}