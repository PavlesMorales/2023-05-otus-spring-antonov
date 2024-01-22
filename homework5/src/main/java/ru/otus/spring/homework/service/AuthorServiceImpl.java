package ru.otus.spring.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.dao.author.AuthorDao;
import ru.otus.spring.homework.domain.author.Author;
import ru.otus.spring.homework.exception.CreationException;
import ru.otus.spring.homework.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public Author create(final Author author) {
        return authorDao.create(author)
                .orElseThrow(() -> new CreationException("Error create author"));
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
    public void delete(long id) {
        authorDao.delete(id);
    }

    @Override
    public Author update(final Author author) {
        authorDao.getById(author.id())
                .orElseThrow(() -> new NotFoundException("Author", author.id()));

        authorDao.update(author);
        return author;
    }

    @Override
    public Author getById(final Long id) {
        return authorDao.getById(id)
                .orElseThrow(() -> new NotFoundException("Author", id));
    }
}
