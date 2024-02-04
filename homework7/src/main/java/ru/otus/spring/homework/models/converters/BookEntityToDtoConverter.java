package ru.otus.spring.homework.models.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.models.EntityToDtoConverter;
import ru.otus.spring.homework.models.dto.AuthorDto;
import ru.otus.spring.homework.models.dto.BookDto;
import ru.otus.spring.homework.models.dto.GenreDto;
import ru.otus.spring.homework.models.entity.Author;
import ru.otus.spring.homework.models.entity.Book;
import ru.otus.spring.homework.models.entity.Genre;

@Component
@RequiredArgsConstructor
public class BookEntityToDtoConverter implements EntityToDtoConverter<Book, BookDto> {

    private final EntityToDtoConverter<Genre, GenreDto> genreConverter;

    private final EntityToDtoConverter<Author, AuthorDto> authorConverter;


    @Override
    public BookDto convert(final Book source) {

        return BookDto.builder()
                .id(source.getId())
                .title(source.getTitle())
                .authorDto(authorConverter.convert(source.getAuthor()))
                .genreDto(genreConverter.convert(source.getGenre()))
                .build();
    }
}
