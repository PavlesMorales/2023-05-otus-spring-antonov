package ru.otus.spring.homework.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.models.entity.Comment;
import ru.otus.spring.homework.repositories.CommentRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> findAllByBookId(final Long bookId) {
        TypedQuery<Comment> query = em.createQuery("""
                select c from Comment c
                where c.book.id =:bookId
                """, Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public Optional<Comment> findById(final Long id) {
        TypedQuery<Comment> query = em.createQuery("""
                select c from Comment c
                where c.id =:id
                """, Comment.class);
        query.setParameter("id", id);
        query.setMaxResults(1);
        return query.getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Comment save(final Comment comment) {
        if (Objects.isNull(comment.getId())) {
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    @Override
    public void deleteById(final Long id) {
        Optional.ofNullable(em.find(Comment.class, id)).ifPresent(em::remove);
    }
}
