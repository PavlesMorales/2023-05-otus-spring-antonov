package ru.otus.spring.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.homework.models.entity.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByBookId(String bookId);

    void deleteAllByBookId(String bookId);
}
