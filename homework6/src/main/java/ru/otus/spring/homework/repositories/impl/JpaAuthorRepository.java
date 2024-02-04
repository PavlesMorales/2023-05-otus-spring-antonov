package ru.otus.spring.homework.repositories.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.models.entity.Author;
import ru.otus.spring.homework.repositories.AbstractRepository;
import ru.otus.spring.homework.repositories.AuthorRepository;

import java.util.List;

@Repository
public class JpaAuthorRepository extends AbstractRepository<Author, Long> implements AuthorRepository {


    @Override
    public List<Author> findAll() {
        return getEm().createQuery("select a from Author a", Author.class)
                .getResultList();
    }

    @Override
    protected Class<Author> getEntityType() {
        return Author.class;
    }

}
