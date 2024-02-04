package ru.otus.spring.homework.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {

    private final Long id;

    private final String text;

    private final String book;

    private final Long bookId;
}
