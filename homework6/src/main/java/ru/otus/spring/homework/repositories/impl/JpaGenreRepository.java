package ru.otus.spring.homework.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.models.entity.Genre;
import ru.otus.spring.homework.repositories.GenreRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaGenreRepository implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g", Genre.class)
                .getResultList();
    }

    @Override
    public Optional<Genre> findById(final Long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public Genre save(final Genre genre) {
        if (Objects.isNull(genre.getId())) {
            em.persist(genre);
            return genre;
        }
        return em.merge(genre);
    }

    @Override
    public void deleteById(final Long id) {
        Optional.ofNullable(em.find(Genre.class, id))
                .ifPresent(em::remove);
    }

}
