package ru.otus.spring.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.dao.genre.GenreDao;
import ru.otus.spring.homework.domain.genre.Genre;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public Optional<Genre> createGenre(String genreName) {
        return genreDao.create(genreName);
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public Optional<Genre> get(long id) {
        return genreDao.get(id);
    }

    @Override
    public boolean delete(long id) {
       return genreDao.delete(id);
    }

    @Override
    public Optional<Genre> update(long id, String newName) {
        return genreDao.update(id, newName);
    }
}
