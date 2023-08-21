package ru.otus.spring.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.dao.author.AuthorDao;
import ru.otus.spring.homework.domain.author.Author;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public Optional<Author> create(String firstName, String lastName) {
        return authorDao.create(firstName, lastName);
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
    public boolean delete(long id) {
        return authorDao.delete(id);
    }

    @Override
    public Optional<Author> update(long id, String firstName, String lastName) {
        return authorDao.update(id, firstName, lastName);
    }

    @Override
    public Optional<Author> getById(Long id) {

        return authorDao.getById(id);
    }
}
