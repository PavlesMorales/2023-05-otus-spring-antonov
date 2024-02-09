package ru.otus.spring.homework.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

    private final String id;

    private final String title;

    private final AuthorDto authorDto;

    private final GenreDto genreDto;
}
