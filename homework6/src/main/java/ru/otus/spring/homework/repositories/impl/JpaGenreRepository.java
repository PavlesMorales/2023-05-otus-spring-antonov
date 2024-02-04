package ru.otus.spring.homework.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.models.entity.Genre;
import ru.otus.spring.homework.repositories.AbstractRepository;
import ru.otus.spring.homework.repositories.GenreRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaGenreRepository extends AbstractRepository<Genre, Long> implements GenreRepository {

    @Override
    public List<Genre> findAll() {
        return getEm().createQuery("select g from Genre g", Genre.class)
                .getResultList();
    }

    @Override
    protected Class<Genre> getEntityType() {
        return Genre.class;
    }

}
