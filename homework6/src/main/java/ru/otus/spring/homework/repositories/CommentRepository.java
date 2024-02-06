package ru.otus.spring.homework.repositories;

import ru.otus.spring.homework.models.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    List<Comment> findAllByBookId(Long bookId);

    Optional<Comment> findById(Long id);

    Comment save(Comment entity);

    void deleteById(Long id);

}
