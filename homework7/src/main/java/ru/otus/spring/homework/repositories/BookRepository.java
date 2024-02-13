package ru.otus.spring.homework.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.lang.NonNull;
import ru.otus.spring.homework.models.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends ListCrudRepository<Book, Long> {

    @NonNull
    @EntityGraph(value = "book-author-genre-entity-graph")
    List<Book> findAll();

    @NonNull
    @EntityGraph(value = "book-author-genre-entity-graph")
    Optional<Book> findById(@NonNull Long id);
}
