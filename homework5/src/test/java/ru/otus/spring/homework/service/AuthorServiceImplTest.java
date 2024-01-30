package ru.otus.spring.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.homework.dao.author.AuthorRepository;
import ru.otus.spring.homework.domain.author.Author;
import ru.otus.spring.homework.exception.CreationException;
import ru.otus.spring.homework.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    AuthorServiceImpl subj;

    @Test
    void shouldCreateAuthor() {
        Author expected = Author.builder()
                .firstName("first_name")
                .lastName("last_name")
                .build();

        Author author = Author.builder()
                .firstName("first_name")
                .lastName("last_name")
                .build();

        when(authorRepository.create(author)).thenReturn(Optional.of(expected));

        Author actual = subj.create(author);

        assertThat(actual).isEqualTo(expected);

        verify(authorRepository, times(1)).create(author);

    }

    @Test
    void shouldThrownCreationException() {
        Author author = Author.builder()
                .firstName("first_name")
                .lastName("last_name")
                .build();

        when(authorRepository.create(author)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> subj.create(author)).isInstanceOf(CreationException.class);

        verify(authorRepository, times(1)).create(author);

    }

    @Test
    void shouldReturnListAuthor() {
        List<Author> expected = List.of(Author.builder()
                        .id(1L)
                        .firstName("first_name")
                        .lastName("last_name")
                        .build(),
                Author.builder()
                        .id(2L)
                        .firstName("first_name_2")
                        .lastName("last_name_2")
                        .build());

        when(authorRepository.getAll()).thenReturn(expected);

        List<Author> actual = subj.getAll();

        assertThat(actual).isEqualTo(expected);

        verify(authorRepository, times(1)).getAll();
    }

    @Test
    void shouldDeleteAuthorById() {
        doNothing().when(authorRepository).delete(1L);

        subj.delete(1L);

        verify(authorRepository, times(1)).delete(1L);
    }

    @Test
    void shouldUpdateAuthor() {
        Author author = Author.builder()
                .id(1L)
                .firstName("first_name")
                .lastName("last_name")
                .build();

        when(authorRepository.getById(1L)).thenReturn(Optional.of(author));
        doNothing().when(authorRepository).update(author);

        subj.update(author);

        verify(authorRepository, times(1)).update(author);
    }

    @Test
    void shouldThrownNotFoundExceptionWhenUpdateAuthor() {
        Author author = Author.builder()
                .id(1L)
                .firstName("first_name")
                .lastName("last_name")
                .build();

        when(authorRepository.getById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> subj.update(author)).isInstanceOf(NotFoundException.class);

        verify(authorRepository, times(1)).getById(1L);
    }

    @Test
    void shouldReturnAuthorById() {
        Author expected = Author.builder()
                .id(1L)
                .firstName("first_name")
                .lastName("last_name")
                .build();

        when(authorRepository.getById(1L)).thenReturn(Optional.of(expected));

        Author actual = subj.getById(1L);

        assertThat(expected).isEqualTo(actual);

        verify(authorRepository, times(1)).getById(1L);
    }

    @Test
    void shouldThrownNotFoundExceptionWhenGetAuthorById() {
        Author author = Author.builder()
                .id(1L)
                .firstName("first_name")
                .lastName("last_name")
                .build();

        when(authorRepository.getById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> subj.update(author)).isInstanceOf(NotFoundException.class);

        verify(authorRepository, times(1)).getById(1L);
    }
}