package ru.otus.spring.homework.commands.converters;

import org.springframework.stereotype.Component;
import ru.otus.spring.homework.models.dto.GenreDto;

@Component
public class GenreConverter {

    public String genreToString(GenreDto genre) {
        return "Id: %d, Name: %s".formatted(genre.getId(), genre.getName());
    }

}
