package ru.otus.spring.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.homework.models.entity.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {

}
