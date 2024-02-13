package ru.otus.spring.homework.services;

import ru.otus.spring.homework.models.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto findById(Long id);

    List<BookDto> findAll();

    BookDto insert(String title, Long authorId, Long genreId);

    BookDto update(Long id, String title, Long authorId, Long genreId);

    void deleteById(Long id);
}
