package ru.otus.spring.homework.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.homework.models.entity.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findAllByBookId(Long bookId);

}
