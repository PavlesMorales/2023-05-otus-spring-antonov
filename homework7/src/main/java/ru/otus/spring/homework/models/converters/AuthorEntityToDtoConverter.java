package ru.otus.spring.homework.models.converters;

import org.springframework.stereotype.Component;
import ru.otus.spring.homework.models.EntityToDtoConverter;
import ru.otus.spring.homework.models.dto.AuthorDto;
import ru.otus.spring.homework.models.entity.Author;

@Component
public class AuthorEntityToDtoConverter implements EntityToDtoConverter<Author, AuthorDto> {

    @Override
    public AuthorDto convert(final Author source) {

        return AuthorDto.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}
