package ru.otus.spring.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.homework.models.entity.Book;

public interface BookRepository extends MongoRepository<Book, String> {

    boolean existsByAuthorId(String authorId);

    boolean existsByGenreId(String genreId);
}
