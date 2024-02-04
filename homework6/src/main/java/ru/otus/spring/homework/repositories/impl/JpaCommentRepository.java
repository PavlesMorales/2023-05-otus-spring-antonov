package ru.otus.spring.homework.repositories.impl;

import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.models.entity.Comment;
import ru.otus.spring.homework.repositories.AbstractRepository;
import ru.otus.spring.homework.repositories.CommentRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository extends AbstractRepository<Comment, Long> implements CommentRepository {

    @Override
    public List<Comment> findAllByBookId(final Long bookId) {
        final TypedQuery<Comment> query = getEm().createQuery(
                """
                        select c from Comment c
                            left join fetch c.book b
                            left join fetch b.author
                            left join fetch b.genre
                        where b.id =:bookId
                        """, Comment.class);

        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    protected Class<Comment> getEntityType() {
        return Comment.class;
    }
}
