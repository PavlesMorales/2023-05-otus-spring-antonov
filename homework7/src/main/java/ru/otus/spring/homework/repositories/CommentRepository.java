package ru.otus.spring.homework.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import ru.otus.spring.homework.models.entity.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long>, ListCrudRepository<Comment, Long> {

    @EntityGraph(attributePaths = "book")
    List<Comment> findAllByBookId(Long bookId);

}
