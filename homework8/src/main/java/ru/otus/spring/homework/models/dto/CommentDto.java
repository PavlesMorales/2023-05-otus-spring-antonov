package ru.otus.spring.homework.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {

    private final String id;

    private final String text;
}
