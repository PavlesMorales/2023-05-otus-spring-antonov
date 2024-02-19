package ru.otus.spring.homework.services;

import ru.otus.spring.homework.models.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> findAllByBookId(Long bookId);

    CommentDto findById(Long id);

    CommentDto create(CommentDto commentDto);

    CommentDto update(CommentDto commentDto);

    void deleteById(Long id);

}
