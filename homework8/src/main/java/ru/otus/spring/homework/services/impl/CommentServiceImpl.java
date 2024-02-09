package ru.otus.spring.homework.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.exceptions.EntityNotFoundException;
import ru.otus.spring.homework.models.converters.CommentEntityToDtoConverter;
import ru.otus.spring.homework.models.dto.CommentDto;
import ru.otus.spring.homework.models.entity.Book;
import ru.otus.spring.homework.models.entity.Comment;
import ru.otus.spring.homework.repositories.BookRepository;
import ru.otus.spring.homework.repositories.CommentRepository;
import ru.otus.spring.homework.services.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final CommentEntityToDtoConverter converter;

    @Override
    public List<CommentDto> findAllByBookId(final String bookId) {
        return commentRepository.findAllByBookId(bookId)
                .stream()
                .map(converter::convert)
                .toList();
    }

    @Override
    public CommentDto findById(final String id) {
        return converter.convert(getComment(id));
    }

    @Override
    public CommentDto create(final String text, final String bookId) {
        final Book book = getBook(bookId);

        final Comment comment = Comment.builder()
                .text(text)
                .book(book)
                .build();

        return converter.convert(commentRepository.save(comment));
    }

    @Override
    public CommentDto update(final String id, final String text) {
        final Comment comment = getComment(id);
        comment.setText(text);
        return converter.convert(commentRepository.save(comment));
    }

    @Override
    public void deleteById(final String id) {
        commentRepository.deleteById(id);
    }

    private Book getBook(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(id)));
    }

    private Comment getComment(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %s not found".formatted(id)));
    }
}
