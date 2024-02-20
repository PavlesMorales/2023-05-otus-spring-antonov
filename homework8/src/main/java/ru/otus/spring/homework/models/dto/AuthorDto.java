package ru.otus.spring.homework.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorDto {

    private final String id;

    private final String firstName;

    private final String lastName;
}
