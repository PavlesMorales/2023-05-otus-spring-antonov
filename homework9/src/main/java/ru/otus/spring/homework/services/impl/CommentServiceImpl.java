package ru.otus.spring.homework.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public CommentDto create(final CommentDto commentDto) {
        final Book book = getBook(commentDto.getBookId());

        final Comment comment = Comment.builder()
                .text(commentDto.getText())
                .book(book)
                .build();

        return converter.convert(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public CommentDto update(final CommentDto commentDto) {
        final Comment comment = getComment(commentDto.getId());
        comment.setText(commentDto.getText());

        return converter.convert(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        commentRepository.deleteById(id);
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
