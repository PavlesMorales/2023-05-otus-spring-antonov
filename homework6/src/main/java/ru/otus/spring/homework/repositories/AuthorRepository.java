package ru.otus.spring.homework.repositories;

import ru.otus.spring.homework.models.entity.Author;

import java.util.List;

public interface AuthorRepository extends Repository<Author, Long> {

    List<Author> findAll();

}
