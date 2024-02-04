package ru.otus.spring.homework.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.homework.exceptions.EntityNotFoundException;
import ru.otus.spring.homework.models.EntityToDtoConverter;
import ru.otus.spring.homework.models.dto.AuthorDto;
import ru.otus.spring.homework.models.entity.Author;
import ru.otus.spring.homework.repositories.impl.JpaAuthorRepository;
import ru.otus.spring.homework.services.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final JpaAuthorRepository authorRepository;

    private final EntityToDtoConverter<Author, AuthorDto> converter;


    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(converter::convert)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDto findById(final Long id) {
        return converter.convert(getAuthor(id));
    }

    @Override
    @Transactional
    public AuthorDto insert(final String firstName, final String lastName) {
        return converter.convert(authorRepository.save(Author.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build())
        );
    }

    @Override
    @Transactional
    public AuthorDto update(final Long id, final String firstName, final String lastName) {
        final Author author = getAuthor(id);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        return converter.convert(authorRepository.save(author));
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        final Author author = getAuthor(id);
        authorRepository.remove(author);
    }

    private Author getAuthor(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found by id: %s".formatted(id)));
    }
}
