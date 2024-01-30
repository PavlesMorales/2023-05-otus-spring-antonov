package ru.otus.spring.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.dao.author.AuthorRepository;
import ru.otus.spring.homework.domain.author.Author;
import ru.otus.spring.homework.exception.CreationException;
import ru.otus.spring.homework.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author create(final Author author) {
        return authorRepository.create(author)
                .orElseThrow(() -> new CreationException("Error create author"));
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.getAll();
    }

    @Override
    public void delete(long id) {
        authorRepository.delete(id);
    }

    @Override
    public Author update(final Author author) {
        authorRepository.getById(author.id())
                .orElseThrow(() -> new NotFoundException("Author", author.id()));

        authorRepository.update(author);
        return author;
    }

    @Override
    public Author getById(final Long id) {
        return authorRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("Author", id));
    }
}
