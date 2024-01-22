package ru.otus.spring.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.dao.genre.GenreDao;
import ru.otus.spring.homework.domain.genre.Genre;
import ru.otus.spring.homework.exception.CreationException;
import ru.otus.spring.homework.exception.NotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public Genre create(final Genre genre) {
        return genreDao.create(genre)
                .orElseThrow(() -> new CreationException("Error create genre"));
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public Genre getById(final Long id) {
        return genreDao.getById(id)
                .orElseThrow(() -> new NotFoundException("Genre", id));
    }

    @Override
    public void delete(final Long id) {
        genreDao.delete(id);
    }

    @Override
    public Genre update(final Genre genre) {
        genreDao.getById(genre.id())
                .orElseThrow(() -> new NotFoundException("Genre", genre.id()));

        genreDao.update(genre);
        return genre;

    }
}
