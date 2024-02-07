package ru.otus.spring.homework.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import ru.otus.spring.homework.models.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @EntityGraph(value = "comment-book-entity-graph")
    List<Comment> findAllByBookId(Long bookId);

    @NonNull
    @EntityGraph(value = "comment-book-entity-graph")
    Optional<Comment> findById(@NonNull Long id);

}
