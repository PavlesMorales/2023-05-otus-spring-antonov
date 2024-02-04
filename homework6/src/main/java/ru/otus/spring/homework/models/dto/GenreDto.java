package ru.otus.spring.homework.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreDto {

    private final Long id;

    private final String name;
}
