package ru.otus.spring.homework.commands.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.models.dto.CommentDto;

@Component
@RequiredArgsConstructor
public class CommentConverter {


    public String commentToString(final CommentDto comment) {
        return "Id: %d, text %s"
                .formatted(comment.getId(), comment.getText());
    }
}
