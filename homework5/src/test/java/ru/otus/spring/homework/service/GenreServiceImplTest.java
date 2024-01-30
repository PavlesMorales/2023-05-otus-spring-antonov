package ru.otus.spring.homework.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.homework.dao.genre.GenreRepository;
import ru.otus.spring.homework.domain.genre.Genre;
import ru.otus.spring.homework.exception.CreationException;
import ru.otus.spring.homework.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    @InjectMocks
    GenreServiceImpl subj;

    @Mock
    GenreRepository genreRepository;

    @Test
    void shouldCreateGenre() {
        String genreName = "some genre name";
        Optional<Genre> expected = Optional.of(Genre.builder().id(1L).genreName(genreName).build());
        Genre genre = Genre.builder().genreName(genreName).build();

        when(genreRepository.create(genre)).thenReturn(expected);

        Genre actual = subj.create(genre);

        Assertions.assertThat(actual).isEqualTo(expected.get());

        verify(genreRepository, times(1)).create(genre);
    }

    @Test
    void shouldThrownCreationException() {
        String genreName = "some genre name";
        Genre genre = Genre.builder().genreName(genreName).build();

        when(genreRepository.create(genre)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> subj.create(genre)).isInstanceOf(CreationException.class);

        verify(genreRepository, times(1)).create(genre);
    }

    @Test
    void shouldReturnAllGenre() {
        List<Genre> expected = List.of(Genre.builder()
                        .id(1L)
                        .genreName("Name1")
                        .build(),
                Genre.builder()
                        .id(1L)
                        .genreName("Name2")
                        .build()
        );

        when(genreRepository.getAll()).thenReturn(expected);

        List<Genre> actual = subj.getAll();

        Assertions.assertThat(actual).isEqualTo(expected);
        verify(genreRepository, times(1)).getAll();
    }

    @Test
    void shouldDeleteGenre() {
        long id = 1L;

        doNothing().when(genreRepository).delete(1L);
        subj.delete(1L);
        verify(genreRepository, times(1)).delete(id);

    }

    @Test
    void shouldUpdateGenre() {
        long id = 1L;
        String newName = "newName";
        Genre genre = Genre.builder().id(id).genreName(newName).build();
        when(genreRepository.getById(id)).thenReturn(Optional.of(genre));
        doNothing().when(genreRepository).update(genre);

        subj.update(genre);

        verify(genreRepository, times(1)).getById(id);
        verify(genreRepository, times(1)).update(genre);
    }

    @Test
    void shouldThrownNotFoundException() {
        long id = 1L;
        String newName = "newName";
        Genre genre = Genre.builder().id(id).genreName(newName).build();
        when(genreRepository.getById(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> subj.update(genre)).isInstanceOf(NotFoundException.class);

        verify(genreRepository, times(1)).getById(id);
    }
}