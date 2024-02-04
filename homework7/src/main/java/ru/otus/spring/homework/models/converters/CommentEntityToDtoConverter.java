package ru.otus.spring.homework.models.converters;

import org.springframework.stereotype.Component;
import ru.otus.spring.homework.models.EntityToDtoConverter;
import ru.otus.spring.homework.models.dto.CommentDto;
import ru.otus.spring.homework.models.entity.Comment;

@Component
public class CommentEntityToDtoConverter implements EntityToDtoConverter<Comment, CommentDto> {

    @Override
    public CommentDto convert(final Comment source) {

        return CommentDto.builder()
                .id(source.getId())
                .text(source.getText())
                .build();
    }
}
