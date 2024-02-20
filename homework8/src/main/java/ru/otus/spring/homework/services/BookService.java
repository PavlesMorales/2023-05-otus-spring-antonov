package ru.otus.spring.homework.services;

import ru.otus.spring.homework.models.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto findById(String id);

    List<BookDto> findAll();

    BookDto create(String title, String authorId, String genreId);

    BookDto update(String id, String title, String authorId, String genreId);

    void deleteById(String id);
}
