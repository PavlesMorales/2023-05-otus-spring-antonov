package ru.otus.spring.homework.veiw;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.InputProvider;
import org.springframework.shell.Shell;
import ru.otus.spring.homework.TestConfig;
import ru.otus.spring.homework.domain.genre.Genre;
import ru.otus.spring.homework.service.GenreService;
import ru.otus.spring.homework.tableconstructor.GenreTableConstructor;

import java.util.List;

import static org.mockito.Mockito.*;

class GenreShellViewTest extends TestConfig {

    @Autowired
    Shell subj;

    @MockBean
    GenreService service;
    @MockBean
    GenreTableConstructor tableConstructor;

    @MockBean
    InputProvider inputProvider;


    @Test
    void shouldCreateGenre() throws Exception {
        String command = "genre-create";
        String parameter = "science";

        Genre genre = Genre.builder()
                .genreName(parameter)
                .build();

        Genre createdGenre = Genre.builder()
                .id(1L)
                .genreName("science")
                .build();

        String expected = "success show created genre";

        when(inputProvider.readInput())
                .thenReturn(() -> "%s %s".formatted(command, parameter))
                .thenReturn(null);
        when(service.create(genre)).thenReturn(createdGenre);
        when(tableConstructor.buildGenreTable(createdGenre)).thenReturn(expected);

        subj.run(inputProvider);

        verify(service, times(1)).create(genre);
        verify(tableConstructor, times(1)).buildGenreTable(createdGenre);
    }

    @Test
    void shouldShowAllGenre() throws Exception {
        String command = "genre-show-all";
        String expected = "success show all genre";
        List<Genre> allGenres = List.of(Genre.builder().id(1L).genreName("science").build());

        when(inputProvider.readInput())
                .thenReturn(() -> command)
                .thenReturn(null);

        when(service.getAll()).thenReturn(allGenres);
        when(tableConstructor.buildGenreTable(allGenres)).thenReturn(expected);

        subj.run(inputProvider);


        verify(service, times(1)).getAll();
        verify(tableConstructor, times(1)).buildGenreTable(allGenres);
    }

    @Test
    void shouldDeleteGenre() throws Exception {
        String command = "genre-delete";
        long id = 1L;
        when(inputProvider.readInput())
                .thenReturn(() -> "%s %s".formatted(command, id))
                .thenReturn(null);


        doNothing().when(service).delete(1L);
        subj.run(inputProvider);

        verify(service, times(1)).delete(id);
        verifyNoInteractions(tableConstructor);
    }

    @Test
    void shouldUpdateGenre() throws Exception {
        String command = "genre-update";
        long id = 1L;
        String newGenreNameParameter = "science";

        final Genre genre = Genre.builder()
                .id(1L)
                .genreName(newGenreNameParameter)
                .build();

        Genre updatedGenre = Genre.builder()
                .id(1L)
                .genreName(newGenreNameParameter)
                .build();

        String expected = "success show updated genre";

        when(inputProvider.readInput())
                .thenReturn(() -> "%s %s %s".formatted(command, id, newGenreNameParameter))
                .thenReturn(null);

        when(service.update(genre)).thenReturn(updatedGenre);
        when(tableConstructor.buildGenreTable(updatedGenre)).thenReturn(expected);

        subj.run(inputProvider);

        verify(service, times(1)).update(genre);
        verify(tableConstructor, times(1)).buildGenreTable(updatedGenre);
    }
}