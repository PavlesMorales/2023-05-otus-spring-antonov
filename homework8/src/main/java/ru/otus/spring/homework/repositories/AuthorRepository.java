package ru.otus.spring.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.homework.models.entity.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

}
