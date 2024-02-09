package ru.otus.spring.homework.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.exceptions.EntityNotFoundException;
import ru.otus.spring.homework.models.converters.AuthorEntityToDtoConverter;
import ru.otus.spring.homework.models.dto.AuthorDto;
import ru.otus.spring.homework.models.entity.Author;
import ru.otus.spring.homework.repositories.AuthorRepository;
import ru.otus.spring.homework.services.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorEntityToDtoConverter converter;

    @Override
    public List<AuthorDto> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(converter::convert)
                .toList();
    }

    @Override
    public AuthorDto findById(final String id) {
        return converter.convert(getAuthor(id));
    }

    @Override
    public AuthorDto create(final String firstName, final String lastName) {
        return converter.convert(authorRepository.save(Author.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build())
        );
    }

    @Override
    public AuthorDto update(final String id, final String firstName, final String lastName) {
        final Author author = getAuthor(id);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        return converter.convert(authorRepository.save(author));
    }

    @Override
    public void deleteById(final String id) {
        authorRepository.deleteById(id);
    }

    private Author getAuthor(String id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found by id: %s".formatted(id)));
    }
}
