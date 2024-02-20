package ru.otus.spring.homework.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public AuthorDto create(AuthorDto authorDto) {
        return converter.convert(authorRepository.save(Author.builder()
                .firstName(authorDto.getFirstName())
                .lastName(authorDto.getLastName())
                .build())
        );
    }

    @Override
    @Transactional
    public AuthorDto update(AuthorDto authorDto) {
        final Author author = getAuthor(authorDto.getId());
        author.setFirstName(authorDto.getFirstName());
        author.setLastName(authorDto.getLastName());
        return converter.convert(authorRepository.save(author));
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        authorRepository.deleteById(id);
    }

    private Author getAuthor(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found by id: %s".formatted(id)));
    }
}
