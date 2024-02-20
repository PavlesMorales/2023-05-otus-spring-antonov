package ru.otus.spring.homework.models.converters;

import org.springframework.stereotype.Component;
import ru.otus.spring.homework.models.dto.GenreDto;
import ru.otus.spring.homework.models.entity.Genre;

@Component
public class GenreEntityToDtoConverter {

    public GenreDto convert(final Genre source) {

        return GenreDto.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}
