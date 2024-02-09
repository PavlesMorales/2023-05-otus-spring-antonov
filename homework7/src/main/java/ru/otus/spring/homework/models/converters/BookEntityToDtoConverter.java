package ru.otus.spring.homework.models.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.models.dto.BookDto;
import ru.otus.spring.homework.models.entity.Book;

@Component
@RequiredArgsConstructor
public class BookEntityToDtoConverter {

    private final GenreEntityToDtoConverter genreConverter;

    private final AuthorEntityToDtoConverter authorConverter;


    public BookDto convert(final Book source) {

        return BookDto.builder()
                .id(source.getId())
                .title(source.getTitle())
                .authorDto(authorConverter.convert(source.getAuthor()))
                .genreDto(genreConverter.convert(source.getGenre()))
                .build();
    }
}
