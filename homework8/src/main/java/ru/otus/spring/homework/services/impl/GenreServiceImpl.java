package ru.otus.spring.homework.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.exceptions.EntityNotFoundException;
import ru.otus.spring.homework.models.converters.GenreEntityToDtoConverter;
import ru.otus.spring.homework.models.dto.GenreDto;
import ru.otus.spring.homework.models.entity.Genre;
import ru.otus.spring.homework.repositories.GenreRepository;
import ru.otus.spring.homework.services.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final GenreEntityToDtoConverter converter;

    @Override
    public List<GenreDto> findAll() {
        return genreRepository.findAll()
                .stream()
                .map(converter::convert)
                .toList();
    }

    @Override
    public GenreDto findById(final String id) {
        return converter.convert(getGenre(id));
    }

    @Override
    public GenreDto create(final String name) {
        final Genre genre = Genre.builder()
                .name(name)
                .build();
        return converter.convert(genreRepository.save(genre));
    }

    @Override
    public GenreDto update(final String id, final String name) {
        final Genre genre = getGenre(id);
        genre.setName(name);
        return converter.convert(genreRepository.save(genre));
    }

    @Override
    public void deleteById(final String id) {
        genreRepository.deleteById(id);
    }

    private Genre getGenre(String id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre not found by id: %s".formatted(id)));
    }
}
