package ru.otus.spring.homework.repositories.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.models.entity.Book;
import ru.otus.spring.homework.repositories.BookRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
@RequiredArgsConstructor
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public Optional<Book> findById(final Long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.id =:id", Book.class);
        query.setHint(FETCH.getKey(), entityGraph);
        query.setParameter("id", id);
        query.setMaxResults(1);

        return query.getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Book> findFirstByAuthorId(final Long authorId) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.author.id =:authorId", Book.class);
        query.setHint(FETCH.getKey(), entityGraph);
        query.setParameter("authorId", authorId);
        query.setMaxResults(1);

        return query.getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Book> findFirstByGenreId(final Long genreId) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.genre.id =:genreId", Book.class);
        query.setHint(FETCH.getKey(), entityGraph);
        query.setParameter("genreId", genreId);
        query.setMaxResults(1);

        return query.getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Book save(final Book book) {
        if (Objects.isNull(book.getId())) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(final Long id) {
        Optional.ofNullable(em.find(Book.class, id))
                .ifPresent(em::remove);
    }
}
