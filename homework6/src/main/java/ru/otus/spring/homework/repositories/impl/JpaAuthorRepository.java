package ru.otus.spring.homework.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.models.entity.Author;
import ru.otus.spring.homework.repositories.AuthorRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaAuthorRepository implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;


    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class)
                .getResultList();
    }


    @Override
    public Optional<Author> findById(final Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Author save(final Author author) {
        if (Objects.isNull(author.getId())) {
            em.persist(author);
            return author;
        }
        return em.merge(author);
    }

    @Override
    public void deleteById(final Long id) {
        Optional.ofNullable(em.find(Author.class, id))
                .ifPresent(em::remove);
    }

}
