package ru.otus.spring.homework.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework.exceptions.EntityNotFoundException;
import ru.otus.spring.homework.models.EntityToDtoConverter;
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

    private final EntityToDtoConverter<Comment, CommentDto> converter;

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAllByBookId(final Long bookId) {
        return commentRepository.findAllByBookId(bookId)
                .stream()
                .map(converter::convert)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto findById(final Long id) {
        return converter.convert(getComment(id));
    }

    @Override
    @Transactional
    public CommentDto insert(final String text, final Long bookId) {
        final Book book = getBook(bookId);

        final Comment comment = Comment.builder()
                .text(text)
                .book(book)
                .build();

        return converter.convert(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public CommentDto update(final Long id, final String text, final Long bookId) {
        final Comment comment = getComment(id);

        final Book book = getBook(bookId);
        comment.setText(text);
        comment.setBook(book);

        return converter.convert(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        final Comment comment = getComment(id);
        commentRepository.remove(comment);
    }

    private Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
    }

    private Comment getComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
    }
}
