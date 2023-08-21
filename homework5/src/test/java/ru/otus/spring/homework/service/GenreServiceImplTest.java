package ru.otus.spring.homework.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.homework.dao.genre.GenreDao;
import ru.otus.spring.homework.domain.genre.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    @InjectMocks
    GenreServiceImpl subj;

    @Mock
    GenreDao genreDao;

    @Test
    void shouldCreateGenre() {
        String genreName = "some genre name";
        Optional<Genre> expected = Optional.of(Genre.builder().id(1L).genreName(genreName).build());

        when(genreDao.create(genreName)).thenReturn(expected);

        Optional<Genre> actual = subj.createGenre(genreName);

        Assertions.assertThat(actual).isPresent();
        Assertions.assertThat(expected).isEqualTo(actual);

        verify(genreDao, times(1)).create(genreName);
    }

    @Test
    void shouldReturnEmptyOptional() {
        String genreName = "some genre name";
        Genre genre = Genre.builder().genreName(genreName).build();

        when(genreDao.create(genreName)).thenReturn(Optional.empty());

        Optional<Genre> actual = subj.createGenre(genreName);
        Assertions.assertThat(actual).isEmpty();

        verify(genreDao, times(1)).create(genreName);
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

        when(genreDao.getAll()).thenReturn(expected);

        List<Genre> actual = subj.getAll();

        Assertions.assertThat(actual).isEqualTo(expected);
        verify(genreDao, times(1)).getAll();
    }

    @Test
    void shouldDeleteGenre() {
        long id = 1L;

        when(genreDao.delete(id)).thenReturn(true);
        boolean actual = subj.delete(id);
        assertThat(actual).isTrue();

        verify(genreDao, times(1)).delete(id);

    }

    @Test
    void shouldUpdateGenre() {
        long id = 1L;
        String newName = "newName";
        Optional<Genre> expected = Optional.of(Genre.builder().id(1L).genreName(newName).build());
        when(genreDao.update(id, newName)).thenReturn(expected);

        Optional<Genre> actual = subj.update(id, newName);
        Assertions.assertThat(actual)
                .isPresent()
                .isEqualTo(expected);

        verify(genreDao, times(1)).update(id, newName);
    }
}