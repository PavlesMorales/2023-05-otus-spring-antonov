package ru.otus.spring.homework.models.converters;

import org.springframework.stereotype.Component;
import ru.otus.spring.homework.models.EntityToDtoConverter;
import ru.otus.spring.homework.models.dto.GenreDto;
import ru.otus.spring.homework.models.entity.Genre;

@Component
public class GenreEntityToDtoConverter implements EntityToDtoConverter<Genre, GenreDto> {

    @Override
    public GenreDto convert(final Genre source) {

        return GenreDto.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}
