package ru.otus.spring.homework.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import ru.otus.spring.homework.models.entity.Author;

public interface AuthorRepository extends CrudRepository<Author, Long>, ListCrudRepository<Author, Long> {

}
