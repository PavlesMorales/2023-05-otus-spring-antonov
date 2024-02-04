package ru.otus.spring.homework.repositories;

import ru.otus.spring.homework.models.entity.Comment;

import java.util.List;

public interface CommentRepository extends Repository<Comment, Long> {

    List<Comment> findAllByBookId(Long bookId);

}
