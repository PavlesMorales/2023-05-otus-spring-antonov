package ru.otus.spring.homework.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.homework.models.entity.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
class JpaCommentRepositoryTest {

    @Autowired
    CommentRepository subj;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    @DisplayName("должен загружать все комментарии по id книги")
    void shouldCorrectFindAllByBookId() {
        final Long bookId = 1L;
        final List<Comment> expected = testEntityManager.getEntityManager()
                .createQuery("""
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
    @DisplayName("должен загружать комментарий по id")
    void shouldCorrectFindByBookId() {
        final Comment expected = testEntityManager.getEntityManager()
                .find(Comment.class, 1L);
        final Optional<Comment> actual = subj.findById(1L);

        assertThat(actual)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}