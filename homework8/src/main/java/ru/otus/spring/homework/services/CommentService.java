package ru.otus.spring.homework.services;

import ru.otus.spring.homework.models.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> findAllByBookId(String bookId);

    CommentDto findById(String id);

    CommentDto create(String comment, String bookId);

    CommentDto update(String id, String comment);

    void deleteById(String id);

}
