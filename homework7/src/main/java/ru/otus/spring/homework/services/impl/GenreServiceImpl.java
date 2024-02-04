package ru.otus.spring.homework.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework.exceptions.EntityNotFoundException;
import ru.otus.spring.homework.models.EntityToDtoConverter;
import ru.otus.spring.homework.models.dto.GenreDto;
import ru.otus.spring.homework.models.entity.Genre;
import ru.otus.spring.homework.repositories.GenreRepository;
import ru.otus.spring.homework.services.GenreService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final EntityToDtoConverter<Genre, GenreDto> converter;

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        return genreRepository.findAll()
                .stream()
                .map(converter::convert)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GenreDto findById(final Long id) {
        return converter.convert(getGenre(id));
    }

    @Override
    @Transactional
    public GenreDto insert(final String name) {
        final Genre genre = Genre.builder()
                .name(name)
                .build();
        return converter.convert(genreRepository.save(genre));
    }

    @Override
    @Transactional
    public GenreDto update(final Long id, final String name) {
        final Genre genre = getGenre(id);
        genre.setName(name);
        return converter.convert(genreRepository.save(genre));
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        genreRepository.deleteById(id);
    }

    private Genre getGenre(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre not found by id: %s".formatted(id)));
    }
}
