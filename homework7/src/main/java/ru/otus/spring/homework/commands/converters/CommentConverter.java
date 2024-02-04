package ru.otus.spring.homework.commands.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.models.dto.CommentDto;

@Component
@RequiredArgsConstructor
public class CommentConverter {


    public String commentToString(final CommentDto comment) {
        return "Book: Id: %d. Title: %s. Id: %d, text %s"
                .formatted(comment.getBookId(), comment.getBook(), comment.getId(), comment.getText());
    }
}
