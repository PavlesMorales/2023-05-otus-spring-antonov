package ru.otus.spring.homework.repositories;

import ru.otus.spring.homework.models.entity.Book;

import java.util.List;

public interface BookRepository extends Repository<Book, Long> {

    List<Book> findAll();

}
