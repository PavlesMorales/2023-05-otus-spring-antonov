package ru.otus.spring.homework.repositories;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.spring.homework.models.entity.Genre;

public interface GenreRepository extends ListCrudRepository<Genre, Long> {

}
